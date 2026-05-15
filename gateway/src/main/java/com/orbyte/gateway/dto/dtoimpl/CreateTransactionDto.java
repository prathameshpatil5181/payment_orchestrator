package com.orbyte.gateway.dto.dtoimpl;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class CreateTransactionDto {
    private BigInteger amount;
    private String currency;
}
