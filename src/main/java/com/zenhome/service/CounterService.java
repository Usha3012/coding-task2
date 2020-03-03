package com.zenhome.service;

import com.zenhome.domain.*;
import com.zenhome.entity.Consumption;
import com.zenhome.entity.Counter;
import com.zenhome.entity.Village;
import com.zenhome.exception.InvalidDurationException;
import com.zenhome.exception.NotFoundException;
import com.zenhome.repository.ConsumptionRepository;
import com.zenhome.repository.CounterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CounterService {
    private static final Pattern DURATION_PATTERN = Pattern.compile("\\b\\d{1,2}h\\b");
    private final CounterRepository counterRepository;
    private final ConsumptionRepository consumptionRepository;

    @Transactional
    public ConsumptionDTO addConsumption(ConsumptionDTO consumptionDTO) {
        Consumption consumption = new Consumption();
        consumption.setAmount(consumptionDTO.getAmount());
        Counter counter = counterRepository.findById(consumptionDTO.getCounterId())
                .orElseThrow(() -> new NotFoundException("Counter with id " + consumptionDTO.getCounterId() + " not exists"));
        consumption.setCounter(counter);
        Consumption savedConsumption = consumptionRepository.save(consumption);
        return ConsumptionDTO.builder()
                .counterId(savedConsumption.getCounterId())
                .amount(savedConsumption.getAmount()).build();
    }

    public CounterDTO getCounterById(int id) {
        Counter counter = counterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Counter with id " + id + " not exists"));
        return CounterDTO.builder()
                .id(String.valueOf(counter.getId()))
                .village(toVillageDTO(counter.getVillage()))
                .build();
    }


    public ConsumptionReportDTO getConsumptions(String duration) {
        int durationInInt = parseDuration(duration);
        List<Consumption> consumptions = consumptionRepository.getConsumptionByDuration(
                OffsetDateTime.now().minusHours(durationInInt));
        log.info("{} consumption found for past {}h", consumptions.size(), durationInInt);

        Map<Village, Double> consumptionPerVillage = consumptions.stream()
                .filter(consumption -> consumption.getVillage() != null)
                .collect(Collectors.groupingBy(Consumption::getVillage
                        , Collectors.summingDouble(consumption -> consumption.getAmount().doubleValue())));

        return toConsumptionReport(consumptionPerVillage);

    }

    private ConsumptionReportDTO toConsumptionReport(Map<Village, Double> consumptionPerVillage) {
        List<VillageConsumptionDTO> villageConsumptionDTOS = consumptionPerVillage.entrySet()
                .stream()
                .map(e -> new VillageConsumptionDTO(e.getKey().getName(), String.valueOf(e.getValue())))
                .collect(Collectors.toList());

        return ConsumptionReportDTO.builder()
                .villages(villageConsumptionDTOS)
                .build();
    }

    private int parseDuration(String duration) {
        Matcher matcher = DURATION_PATTERN.matcher(duration);
        if (matcher.matches()) {
            try {
                return Integer.parseInt(duration.substring(0, duration.indexOf("h")));
            } catch (Exception ex) {
                throw new InvalidDurationException(duration);
            }
        } else {
            throw new InvalidDurationException(duration);
        }
    }


    private VillageDTO toVillageDTO(Village village) {
        return VillageDTO.builder()
                .id(String.valueOf(village.getId()))
                .name(village.getName())
                .build();
    }

}
