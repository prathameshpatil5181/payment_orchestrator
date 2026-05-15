package com.orbyte.orchestrator.dtos.StripeDtos;

import lombok.Data;

@Data
public class StripeCreatePaymentRequestDTO {
    private String amount;
    private String currency;
    private String token;
    private String paymentType;
}
