package com.orbyte.orchestrator.controllers;

import com.orbyte.orchestrator.dtos.CardPaymentResponse;
import com.orbyte.orchestrator.dtos.CardProcRequest;
import com.orbyte.orchestrator.entity.Txn;
import com.orbyte.orchestrator.factory.CardHandlerFactory;
import com.orbyte.orchestrator.service.Card;
import com.orbyte.orchestrator.service.impl.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/card")
    public ResponseEntity<Txn> createCardPayment(@RequestBody CardProcRequest cardProcRequest, @RequestParam String txnId){

        System.out.println(txnId);
        Txn resp  = cardService.processCardRequest(cardProcRequest,UUID.fromString(txnId));

        return new ResponseEntity<>(resp, HttpStatus.OK);

    }
}