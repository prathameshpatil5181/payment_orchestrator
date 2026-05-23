package com.orbyte.tokenizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class EncryptTokenResponse {
    private String cardNumber;
    private int expiryMonth;
    private int expiryYear;
    private String token;
}
