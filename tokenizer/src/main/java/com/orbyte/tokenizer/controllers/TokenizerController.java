package com.orbyte.tokenizer.controllers;

import com.orbyte.tokenizer.dto.CardInfo;
import com.orbyte.tokenizer.dto.DecryptTokenRequestResponse;
import com.orbyte.tokenizer.dto.EncryptTokenResponse;
import com.orbyte.tokenizer.dto.KeyGenerationResponse;
import com.orbyte.tokenizer.entity.OrbToken;
import com.orbyte.tokenizer.exceptions.TokenErrorResponseException;
import com.orbyte.tokenizer.services.CardTokenizer;
import com.orbyte.tokenizer.services.KeyGenerationService;
import com.orbyte.tokenizer.services.OrbyteTokenService;
import com.orbyte.tokenizer.services.impl.StripeTokenizerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/cardtoken")
@RequiredArgsConstructor
@Slf4j
public class TokenizerController {


    private String password = "Abhi@6705";

    private final CardTokenizer tokenizer;
    private final KeyGenerationService keyGenerationService;
    private final OrbyteTokenService orbyteTokenService;

    @PostMapping("/tokenize")
    public ResponseEntity<OrbToken> createToken(@RequestBody CardInfo cardInfo) {

            OrbToken token = tokenizer.createCardToken(cardInfo);
            return new ResponseEntity<>(token, HttpStatus.OK);

    }

    @GetMapping("generate_key")
    public ResponseEntity<KeyGenerationResponse> keyGenerationResponse(){
        return ResponseEntity.ok(keyGenerationService.generateSecureKey());
    }

    @PostMapping("/orb_tokenize")
    public ResponseEntity<EncryptTokenResponse> createOrbToken(@RequestBody CardInfo cardInfo) {

        EncryptTokenResponse response = orbyteTokenService.getEncryptedPan(cardInfo);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/orb_decrypt")
    public ResponseEntity<DecryptTokenRequestResponse> decryptOrbToken(@RequestBody DecryptTokenRequestResponse request) {
        if (!Objects.equals(request.getPassword(), password)){
            return new ResponseEntity<>(request,HttpStatus.BAD_REQUEST);
        }
        CardInfo response = orbyteTokenService.getDecryptedPan(request.getToken());
        request.setCardInfo(response);
        return ResponseEntity.ok(request);
    }


}
