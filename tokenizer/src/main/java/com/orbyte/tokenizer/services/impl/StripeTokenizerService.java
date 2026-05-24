package com.orbyte.tokenizer.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbyte.constants.ModifiedBy;
import com.orbyte.constants.PaymentType;
import com.orbyte.constants.Processor;
import com.orbyte.constants.TokenProviders;
import com.orbyte.tokenizer.cache.CacheService;
import com.orbyte.tokenizer.constants.TokenizerConstants;
import com.orbyte.tokenizer.dto.CardInfo;
import com.orbyte.tokenizer.dto.stripe.StripeCardTokenResponse;
import com.orbyte.tokenizer.entity.OrbToken;
import com.orbyte.tokenizer.exceptions.TokenErrorResponseException;
import com.orbyte.tokenizer.repository.OrbTokenRepository;
import com.orbyte.tokenizer.services.CardTokenizer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;


@Service
@RequiredArgsConstructor
@Slf4j
public class StripeTokenizerService implements CardTokenizer {

    private final RestClient restClient;

    private final OrbTokenRepository orbTokenRepository;
    private final CacheService cacheService;


    @Override
    public Processor getProcessor() {
        return Processor.STRIPE;
    }

    @Transactional
    public OrbToken createCardToken(@NonNull CardInfo cardInfo) {

        log.info("Inside CreateCardTokenService of Stripe");

        //create the form data

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        formData.add("card[number]", cardInfo.getCardNumber());
        formData.add("card[exp_month]", cardInfo.getExpiryMonth());
        formData.add("card[exp_year]", cardInfo.getExpiryYear());
        formData.add("card[cvc]", cardInfo.getCvv());

        // call the stripe tokenization api

        try {

            String publicKey = cacheService.getConfigFromCache(TokenizerConstants.STRIPE_PUBLIC_KEY);
            String stripeCreateTokenUri = cacheService.getConfigFromCache(TokenizerConstants.STRIPE_CREATETOKEN_URI);

            log.info("Stripe publisheable key is {}",stripeCreateTokenUri);

            StripeCardTokenResponse stripeTokenResponse = restClient.post().uri(stripeCreateTokenUri).contentType(MediaType.APPLICATION_FORM_URLENCODED).
                    body(formData).
                    headers(header -> {
                        header.setBearerAuth(publicKey);
                        header.add("Stripe-Version", "2026-04-22.preview");
                    })
                    .retrieve().
                    body(StripeCardTokenResponse.class);

            ObjectMapper mapper = new ObjectMapper();
            String cardPayload;
            if (stripeTokenResponse == null) {
                throw new TokenErrorResponseException("Empty response from Stripe", HttpStatus.BAD_GATEWAY);
            }
            if (stripeTokenResponse.getCard() != null) {
                cardPayload = mapper.writeValueAsString(stripeTokenResponse.getCard());
            } else {
                cardPayload = null;
            }

            // store in database

            OrbToken orbToken = OrbToken.builder().tokenId(stripeTokenResponse.getId()).type(PaymentType.CARD).provider(TokenProviders.STRIPE).used(stripeTokenResponse.getUsed()).created(stripeTokenResponse.getCreated()).modifiedBy(ModifiedBy.TOKENIZER).cardPayload(cardPayload).build();

            orbTokenRepository.save(orbToken);
            log.info("Saved token successfully {}", orbToken );
            return orbToken;

        } catch (HttpClientErrorException ex) {
            String errorBody = ex.getResponseBodyAsString();
            log.error("Client error {} ", errorBody);
            throw new TokenErrorResponseException(errorBody, HttpStatus.BAD_REQUEST);
        } catch (HttpServerErrorException ex) {
            log.error("Server error: {}", ex.getStatusCode());
            throw ex;
        } catch (JsonProcessingException e) {
            log.error("Json error: {}",e.getMessage());
            throw new RuntimeException("failed to create the json object");
        }
    }

}
