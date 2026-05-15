package com.orbyte.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentKeyDTO {
    private String currency;
    private BigInteger amount;
    private LocalDateTime transactionDate;
    private String secret;
}
