package com.orbyte.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrbPaymentResponse {
    private String txnId;
    private String status;
    private String subStatus;
    private String paymentMethod;
    private BigInteger amount;
    private String currency;
    private LocalDateTime txnDate;
    private String redirectUrl;
}
