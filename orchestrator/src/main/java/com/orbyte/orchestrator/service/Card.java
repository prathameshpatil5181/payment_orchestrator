package com.orbyte.orchestrator.service;

import com.orbyte.constants.Processor;
import com.orbyte.dto.PaymentRequest;
import com.orbyte.orchestrator.dtos.StripeDtos.CardTxnResult;
import com.orbyte.orchestrator.entity.Txn;

public interface Card {

    public Processor getProcessor();
    public CardTxnResult process(PaymentRequest paymentRequest);

}
