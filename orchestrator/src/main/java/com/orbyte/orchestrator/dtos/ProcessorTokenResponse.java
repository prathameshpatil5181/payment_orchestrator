package com.orbyte.orchestrator.dtos;

import com.orbyte.constants.Processor;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProcessorTokenResponse {
    private String processorToken;
    private Processor processor;
}
