package com.orbyte.gateway.dto.routerDto;

import com.orbyte.constants.Processor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouterResponse {
    private Processor primaryProcessor;
    private Processor failoverProcessor;
    private Set<String> failoverCodes;
}
