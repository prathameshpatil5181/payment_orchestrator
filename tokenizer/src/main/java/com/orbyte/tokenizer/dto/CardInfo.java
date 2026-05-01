package com.orbyte.tokenizer.dto;

import lombok.Data;

@Data
public class CardInfo {
    private String cardNumber;
    private String cvv;
    private int expiryMonth;
    private int expiryYear;
}
