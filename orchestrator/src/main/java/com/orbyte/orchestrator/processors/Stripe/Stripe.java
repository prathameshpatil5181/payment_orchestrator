package com.orbyte.orchestrator.processors.Stripe;

import com.orbyte.constants.PaymentType;
import com.orbyte.orchestrator.cache.CacheService;
import com.orbyte.orchestrator.constants.StripeConstants;
import com.orbyte.orchestrator.dtos.StripeDtos.StripePaymentDetailsDto;
import com.orbyte.orchestrator.exceptions.StripeResponseErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.util.EnumSet;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class Stripe {

    private final RestClient restClient;
    private final CacheService cacheService;
    private final String STRIPE_CREATE_PAYMENTMETHOD_URI = "stripe_payment_method_uri";
    private final String STRIPE_VERSION = "2026-04-22.preview";
    // remove after implementation of db fetch
    private final String STRIPE_SECRET = "stripe_secret_key";

    private final String STRIPE_CREATE_PAYMENTINTENT_URI = "stripe_payment_intent_uri";

    private final EnumSet<PaymentType> STRIPE_SUPPORTED_PAYMENT_METHODS = EnumSet.of(PaymentType.CARD);


    public String createPaymentMethod(MultiValueMap<String, Object> form) {
        log.info("inside stripe create payment method");

        try {
            log.info("calling stripe card endpoint with {}", form);

            String stripeCreatePaymentApi = cacheService.getConfigFromCache(STRIPE_CREATE_PAYMENTMETHOD_URI);

            String secretToken = cacheService.getConfigFromCache(STRIPE_SECRET);


            Map<String, Object> result =
                    restClient.post()
                            .uri(stripeCreatePaymentApi)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .headers(header -> {
                                header.setBearerAuth(secretToken);
                                header.set("Stripe-Version", STRIPE_VERSION);
                            })
                            .body(form)
                            .retrieve()
                            .body(new ParameterizedTypeReference<Map<String, Object>>() {
                            });

            log.debug("Stripe create payment method response {}", result.toString());

            return result.get("id").toString();
    
        } catch (HttpClientErrorException ex) {
            String errorBody = ex.getResponseBodyAsString();
            log.error("Client error {} ", errorBody);
            throw ex;
        } catch (HttpServerErrorException ex) {
            log.error("Server error: {}", ex.getStatusCode());
            throw ex;
        }

    }

    public String createPaymentIntent(MultiValueMap<String, Object> form) {
        log.info("inside createPaymentIntent");

        try {

            log.info("calling stripe paymentIntent endpoint with {}", form);

            String stripeCreatePayentIntentApi = cacheService.getConfigFromCache(StripeConstants.STRIPE_CREATE_PAYMENTINTENT_URI);

            String secretToken = cacheService.getConfigFromCache(STRIPE_SECRET);

            return restClient.post().uri(stripeCreatePayentIntentApi).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .headers(header -> {
                        header.setBearerAuth(secretToken);
                        header.set("Stripe-Version", StripeConstants.STRIPE_VERSION);
                    })
                    .body(form).retrieve().body(String.class);

        } catch (HttpClientErrorException ex) {
            String errorBody = ex.getResponseBodyAsString();
            log.error("Client error {} ", errorBody);
            throw new StripeResponseErrorException(ex.getStatusText(), (HttpStatus) ex.getStatusCode());
        } catch (HttpServerErrorException ex) {
            log.error("Server error: {}", ex.getStatusCode());
            throw new StripeResponseErrorException(ex.getStatusText(), (HttpStatus) ex.getStatusCode());

        }

    }


}
