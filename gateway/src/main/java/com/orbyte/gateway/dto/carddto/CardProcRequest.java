package com.orbyte.gateway.dto.carddto;

import com.orbyte.constants.Processor;
import com.orbyte.dto.PaymentRequest;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CardProcRequest {
    private Processor processor;
    private Processor primaryProcessor;
    private Processor failoverProcessor;
    private Set<String> failoverCodes;
    private PaymentRequest paymentRequest;
}
