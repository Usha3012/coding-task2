package com.zenhome.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ConsumptionReportDTO {
    List<VillageConsumptionDTO> villages;
}
