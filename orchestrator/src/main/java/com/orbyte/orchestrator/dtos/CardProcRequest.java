package com.orbyte.orchestrator.dtos;

import com.orbyte.constants.Processor;
import com.orbyte.dto.PaymentRequest;
import lombok.Data;

import java.util.Set;

@Data
public class CardProcRequest  {
    private Processor processor;
    private Processor primaryProcessor;
    private Processor failoverProcessor;
    private Set<String> failoverCodes;
    private PaymentRequest paymentRequest;
}
