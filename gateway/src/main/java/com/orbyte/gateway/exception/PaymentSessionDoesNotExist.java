package com.orbyte.gateway.exception;

public class PaymentSessionDoesNotExist extends RuntimeException {
    public PaymentSessionDoesNotExist() {
        super("Payment session does not exits. Create new payment session to proceed");
    }
}
