package com.orbyte.tokenizer.controllers;

import com.orbyte.tokenizer.dto.CardInfo;
import com.orbyte.tokenizer.entity.OrbToken;
import com.orbyte.tokenizer.exceptions.TokenErrorResponseException;
import com.orbyte.tokenizer.services.CardTokenizer;
import com.orbyte.tokenizer.services.impl.StripeTokenizerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cardtoken")
@RequiredArgsConstructor
@Slf4j
public class TokenizerController {

    @Autowired
    CardTokenizer tokenizer;

    @PostMapping("/tokenize")

    public ResponseEntity<OrbToken> createToken(@RequestBody CardInfo cardInfo) {

            OrbToken token = tokenizer.createCardToken(cardInfo);
            return new ResponseEntity<>(token, HttpStatus.OK);

    }

    @ExceptionHandler(TokenErrorResponseException.class)
    public ResponseEntity<Object> handleTokenCreationException(TokenErrorResponseException ex) {
        log.error("Token error: {}", ex.getMessage(), ex);

        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("status", ex.getStatus().value());

        return new ResponseEntity<>(body, ex.getStatus());
    }

}
