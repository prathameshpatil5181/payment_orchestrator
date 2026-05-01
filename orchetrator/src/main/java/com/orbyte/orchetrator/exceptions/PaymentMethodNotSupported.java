package com.orbyte.orchetrator.exceptions;

public class PaymentMethodNotSupported extends RuntimeException {
    public PaymentMethodNotSupported(String paymentMethod) {
        super("PaymentMethod " + paymentMethod +  " is not supportd");
    }
}
