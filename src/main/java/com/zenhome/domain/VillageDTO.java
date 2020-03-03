package com.zenhome.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VillageDTO {
    private String id;
    private String name;
}
