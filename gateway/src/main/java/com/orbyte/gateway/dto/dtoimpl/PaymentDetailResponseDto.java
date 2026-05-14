package com.orbyte.gateway.dto.dtoimpl;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigInteger;

@Data
@Builder
@ToString
public class PaymentDetailResponseDto {
    private BigInteger amount;
    private String currency;
    private String paymentSession;
}
