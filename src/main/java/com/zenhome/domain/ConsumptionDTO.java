package com.zenhome.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class ConsumptionDTO {
    @NotNull
    @JsonProperty("counter_id")
    private Integer counterId;
    @NotNull
    @DecimalMin("0")
    private BigDecimal amount;
}
