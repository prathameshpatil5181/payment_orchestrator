package com.orbyte.orchestrator.service;

import com.orbyte.constants.Processor;
import com.orbyte.dto.PaymentRequest;

public interface Card {

    public Processor getProcessor();
    public String process(PaymentRequest paymentRequest);

}
