package com.orbyte.tokenizer.dto;

import com.orbyte.tokenizer.constants.TokenizerConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KeyGenerationResponse {
    private final String alert = TokenizerConstants.KEY_GENERATION_ALERT;
    private String token;
}
