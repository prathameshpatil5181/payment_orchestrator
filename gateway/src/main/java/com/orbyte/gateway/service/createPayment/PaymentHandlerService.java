package com.orbyte.gateway.service.createPayment;

import com.orbyte.dto.PaymentRequest;
import com.orbyte.gateway.entity.Session;
import com.orbyte.gateway.exception.PaymentSessionDoesNotExist;
import com.orbyte.gateway.service.PaymentHandler;
import com.orbyte.gateway.utility.PaymentHandlerFactory;
import io.lettuce.core.ScriptOutputType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentHandlerService {
    private final CreateOrbPaymentService createOrbPaymentService;
    private final PaymentHandlerFactory paymentHandlerFactory;


    public Object initiatePaymentHandler(@NonNull  String sessionId, @NonNull String transactionId, @NonNull PaymentRequest paymentRequest){
        log.info("Inside PaymentHandlerService.initiatePaymentHandler");
        Session session = Session.builder().sessionId(sessionId).amount(paymentRequest.getAmount()).currency(paymentRequest.getCurrency()).build();


        if (! createOrbPaymentService.isValidSession(session)) throw new PaymentSessionDoesNotExist();

        // call the application api

        PaymentHandler paymentHandler =  paymentHandlerFactory.getPaymenHandler(paymentRequest.getPaymentType());
        log.info("got paymentHandler");
        Object response  = paymentHandler.process(transactionId, paymentRequest); 

        return response;

    }

}
