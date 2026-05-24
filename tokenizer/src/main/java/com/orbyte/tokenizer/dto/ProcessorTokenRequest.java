package com.orbyte.tokenizer.dto;

import com.orbyte.constants.Processor;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessorTokenRequest {
    private String OrbToken;
    private Processor processor;
}
