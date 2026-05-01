package com.orbyte.orchetrator.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfigKeyvalueDto {
    private String name;
    private String value;
}
