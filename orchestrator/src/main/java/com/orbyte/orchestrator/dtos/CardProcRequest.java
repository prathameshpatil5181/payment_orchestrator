package com.orbyte.orchestrator.dtos;

import com.orbyte.constants.Processor;
import com.orbyte.dto.PaymentRequest;
import lombok.Data;

@Data
public class CardProcRequest  {
    Processor processor;
    PaymentRequest paymentRequest;
}
