package com.orbyte.orchestrator.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfigKeyvalueDto {
    private String name;
    private String value;
}
