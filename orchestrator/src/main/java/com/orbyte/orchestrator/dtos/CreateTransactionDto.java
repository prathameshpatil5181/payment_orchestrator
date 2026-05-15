package com.orbyte.orchestrator.dtos;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CreateTransactionDto {
    private BigInteger amount;
    private String currency;
}
