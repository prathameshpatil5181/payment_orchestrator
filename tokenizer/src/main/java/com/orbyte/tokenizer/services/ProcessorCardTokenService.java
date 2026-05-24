package com.orbyte.tokenizer.services;

import com.orbyte.tokenizer.dto.CardInfo;
import com.orbyte.tokenizer.dto.ProcessorTokenRequest;
import com.orbyte.tokenizer.dto.ProcessorTokenResponse;
import com.orbyte.tokenizer.entity.OrbToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessorCardTokenService {

    private final CardTokenizerFactory cardTokenizers;
    private final OrbyteTokenService orbyteTokenService;

    public ProcessorTokenResponse getToken(ProcessorTokenRequest processorTokenRequest){
        log.info("inside ProcessorCardTokenService,getToken");
        CardInfo cardInfo = orbyteTokenService.getDecryptedPan(processorTokenRequest.getOrbToken());

        CardTokenizer cardTokenizer = cardTokenizers.getCardTokenizer(processorTokenRequest.getProcessor());

        OrbToken orbToken =  cardTokenizer.createCardToken(cardInfo);

        ProcessorTokenResponse processorToken = ProcessorTokenResponse.builder().processor(cardTokenizer.getProcessor()).processorToken(orbToken.getTokenId()).build();

        log.info("processor token is {}",processorToken.toString());

        cardInfo.setCardNumber("");
        cardInfo.setCvv("");

        return processorToken;

    }

}
