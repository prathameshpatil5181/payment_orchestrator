package com.orbyte.gateway.dto.dtoimpl;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class PaymentSessionResponse {
    private BigInteger amount;
    private String currency;
    private String sessionId;
    private String transactionId;
}
