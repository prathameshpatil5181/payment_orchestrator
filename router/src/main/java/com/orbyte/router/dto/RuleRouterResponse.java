package com.orbyte.router.dto;

import com.orbyte.constants.Processor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleRouterResponse {
    private Processor primaryProcessor;
    private Processor failoverProcessor;
    private Set<String> failoverCodes;
}
