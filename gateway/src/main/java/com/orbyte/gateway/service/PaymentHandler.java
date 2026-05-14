package com.orbyte.gateway.service;

import com.orbyte.constants.PaymentType;
import com.orbyte.dto.PaymentRequest;

public interface PaymentHandler {
    PaymentType supportedPaymentType();
    Object process(String transactionId ,PaymentRequest paymentRequest);
}
