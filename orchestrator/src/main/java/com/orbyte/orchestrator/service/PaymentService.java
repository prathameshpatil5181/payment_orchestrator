package com.orbyte.orchestrator.service;

public interface PaymentService <T>{
    public String createPayment( T requestDto);
}
