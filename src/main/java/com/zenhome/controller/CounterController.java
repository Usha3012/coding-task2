package com.zenhome.controller;

import com.zenhome.domain.ConsumptionDTO;
import com.zenhome.domain.ConsumptionReportDTO;
import com.zenhome.domain.CounterDTO;
import com.zenhome.service.CounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class CounterController {

    private final CounterService counterService;

    @RequestMapping(path = "/counter_callback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsumptionDTO> reportConsumption(@RequestBody @Valid ConsumptionDTO consumptionDTO) {
        ConsumptionDTO savedConsumptionDTO = counterService.addConsumption(consumptionDTO);
        return new ResponseEntity<>(savedConsumptionDTO, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/counters/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CounterDTO> getCounterById(@PathVariable @NotNull Integer id) {
        CounterDTO counterDTO = counterService.getCounterById(id);
        return new ResponseEntity<>(counterDTO, HttpStatus.OK);
    }

    @RequestMapping(path = "/consumption_reports", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsumptionReportDTO> getConsumptionReports(@RequestParam(name = "duration", defaultValue = "24h")
                                                                              String duration) {
        ConsumptionReportDTO consumptionReportDTO = counterService.getConsumptions(duration);
        return new ResponseEntity<>(consumptionReportDTO, HttpStatus.OK);
    }


}
