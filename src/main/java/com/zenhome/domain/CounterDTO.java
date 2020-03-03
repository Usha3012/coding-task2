package com.zenhome.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CounterDTO {

    private String id;
    private VillageDTO village;
}
