package com.orbyte.gateway.utility;

import com.orbyte.constants.PaymentType;
import com.orbyte.gateway.service.PaymentHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PaymentHandlerFactory {
    private final Map<PaymentType, PaymentHandler>handlers;

    public PaymentHandlerFactory(List<PaymentHandler> handlerList){
        this.handlers = handlerList.stream().collect(Collectors.toMap(
                PaymentHandler::supportedPaymentType,
                Function.identity()
        ));
    }

    public PaymentHandler getPaymenHandler(PaymentType paymentType){
        log.info("inside PaymentHandlerFactory.getPaymenHandler");
        log.info(String.valueOf(paymentType));
        log.info(this.handlers.toString());
        return Optional.ofNullable(handlers.get(paymentType)).orElseThrow(()->new IllegalArgumentException("Payment type not supported"));
    }
}
