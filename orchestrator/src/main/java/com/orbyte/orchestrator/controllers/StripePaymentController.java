package com.orbyte.orchestrator.controllers;


import com.orbyte.orchestrator.dtos.StripeDtos.StripeCreatePaymentRequestDTO;
import com.orbyte.orchestrator.exceptions.PaymentMethodNotSupported;
import com.orbyte.orchestrator.exceptions.StripeResponseErrorException;
import com.orbyte.orchestrator.service.impl.StripeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/stripe")
@RequiredArgsConstructor
@Slf4j
public class StripePaymentController {

    private final StripeService stripeService;

    @PostMapping("/createPayment")
    public ResponseEntity<?> createPayment(@RequestBody  StripeCreatePaymentRequestDTO stripeCreatePaymentRequestDTO){

       var response =  stripeService.createPayment(stripeCreatePaymentRequestDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ExceptionHandler(StripeResponseErrorException.class)
    public ResponseEntity<Object> handleTokenCreationException(StripeResponseErrorException ex) {
        log.error("Token error: {}", ex.getMessage(), ex);

        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("status", ex.getStatus().value());

        return new ResponseEntity<>(body, ex.getStatus());
    }

    @ExceptionHandler(PaymentMethodNotSupported.class)
    public ResponseEntity<Object> handlePaymentMethodNotSupportd(PaymentMethodNotSupported ex) {
        log.error("Token error: {}", ex.getMessage(), ex);

        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.METHOD_NOT_ALLOWED);
    }

}
