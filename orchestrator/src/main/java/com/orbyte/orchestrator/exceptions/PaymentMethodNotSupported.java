package com.orbyte.orchestrator.exceptions;

public class PaymentMethodNotSupported extends RuntimeException {
    public PaymentMethodNotSupported(String paymentMethod) {
        super("PaymentMethod " + paymentMethod +  " is not supportd");
    }
}
