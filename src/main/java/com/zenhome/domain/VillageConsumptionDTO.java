package com.zenhome.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VillageConsumptionDTO {
    @JsonProperty("village_name")
    private String villageName;
    private String consumption;
}
