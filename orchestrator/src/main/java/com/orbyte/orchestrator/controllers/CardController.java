package com.orbyte.orchestrator.controllers;

import com.orbyte.orchestrator.dtos.CardProcRequest;
import com.orbyte.orchestrator.factory.CardHandlerFactory;
import com.orbyte.orchestrator.service.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardController {


    private final CardHandlerFactory cardHandlerFactory;

    @PostMapping("/card")
    public ResponseEntity<String> createCardPayment(@RequestBody CardProcRequest cardProcRequest){

        Card cardHandler = cardHandlerFactory.getCardProcessor(cardProcRequest.getProcessor());

        String resp = cardHandler.process(cardProcRequest.getPaymentRequest());

        return new ResponseEntity<>(resp, HttpStatus.OK);

    }
}