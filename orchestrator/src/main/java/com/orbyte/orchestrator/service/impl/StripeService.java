package com.orbyte.orchestrator.service.impl;

import com.orbyte.constants.PaymentType;
import com.orbyte.orchestrator.cache.CacheService;
import com.orbyte.orchestrator.dtos.StripeDtos.StripeCreatePaymentRequestDTO;
import com.orbyte.orchestrator.exceptions.PaymentMethodNotSupported;
import com.orbyte.orchestrator.constants.StripeConstants;
import com.orbyte.orchestrator.dtos.StripeDtos.StripePaymentDetailsDto;
import com.orbyte.orchestrator.dtos.StripePaymentMethodDTO;
import com.orbyte.orchestrator.exceptions.StripeResponseErrorException;
import com.orbyte.orchestrator.service.PaymentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.util.EnumSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripeService implements PaymentService <StripeCreatePaymentRequestDTO> {


    private final RestClient restClient;

    private final CacheService cacheService ;

    private final EnumSet<PaymentType> supportedPaymentTypes = StripeConstants.STRIPE_SUPPORTED_PAYMENT_METHODS;

    public boolean isPaymentMethodSupported(PaymentType paymentType){
        return supportedPaymentTypes.contains(paymentType);
    }


    public void processCardPayment(){

    }



    @Override
//    @Transactional
    public String createPayment(@NonNull StripeCreatePaymentRequestDTO stripeCreatePaymentRequestDTO) {

        log.info("inside createPayment");

        // create the payment method with stripe

        try{

            String secretToken = cacheService.getConfigFromCache(StripeConstants.STRIPE_SECRET);

                String paymentMethodId = createPaymentMethod(stripeCreatePaymentRequestDTO.getPaymentType(),stripeCreatePaymentRequestDTO.getToken(),secretToken);

            // store the upcoming response in db;
            StripePaymentDetailsDto paymentDetails = StripePaymentDetailsDto.builder().amount("400").currency("USD").paymentMethod(paymentMethodId).confirm("true").build();

            log.info("Create payment Response {}",paymentDetails);

            // create the payment intent
            String paymentIntentResponse = createPaymentIntent(paymentDetails,secretToken);

            log.info("Payment is completed with {}",paymentIntentResponse);

            return paymentIntentResponse;

        }
        catch (PaymentMethodNotSupported paymentMethodNotSupported){
            throw paymentMethodNotSupported;
        }
        catch (RuntimeException exception){
            log.error(exception.getMessage());
            throw exception;
        }


    }

    private String createPaymentMethod(String paymentMethod, String token, String secretKey) {

        log.info("inside createPaymentMethod");

        //create the form
        if(paymentMethod.equals(StripeConstants.CARD_PM)){
            log.info("Starting the card payment");
            StripePaymentMethodDTO cardResponse = createCardPayment(token,secretKey);
            return cardResponse.getId();
        }
        else {
            log.error("payment method is not supproted {}",paymentMethod);
            throw new PaymentMethodNotSupported(paymentMethod);
        }
    }


    private StripePaymentMethodDTO createCardPayment(String token, String secretToken) {

        log.info("inside createCardPayment");

        /*create form*/
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.set("type", StripeConstants.CARD_PM);
        form.set("card[token]", token);

        try {
                log.info("calling stripe card endpoint with {}", form);

                String stripeCreatePaymentApi = cacheService.getConfigFromCache(StripeConstants.STRIPE_CREATE_PAYMENTMETHOD_URI);



            StripePaymentMethodDTO stripeResponse = restClient.post().uri(stripeCreatePaymentApi).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .headers(header -> {
                        header.setBearerAuth(secretToken);
                        header.set("Stripe-Version", StripeConstants.STRIPE_VERSION);
                    })
                    .body(form).retrieve().body(StripePaymentMethodDTO.class);

            log.debug("Stripe create paymethod response ", stripeResponse.toString());
            return  stripeResponse;

        } catch (HttpClientErrorException ex) {
            String errorBody = ex.getResponseBodyAsString();
            log.error("Client error {} ", errorBody);
            throw new StripeResponseErrorException(ex.getStatusText(), (HttpStatus) ex.getStatusCode());
        } catch (HttpServerErrorException ex) {
            log.error("Server error: {}", ex.getStatusCode());
            throw new StripeResponseErrorException(ex.getStatusText(), (HttpStatus) ex.getStatusCode());

        }
    }

    private String createPaymentIntent(StripePaymentDetailsDto paymentDetails,String secretToken){


        log.info("inside createPaymentIntent");
        //create form

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.set("amount", paymentDetails.getAmount());
        form.set("currency", paymentDetails.getCurrency());
        form.set("payment_method", paymentDetails.getPaymentMethod());
        form.set("confirm", paymentDetails.getConfirm());
        form.set("return_url","http://localhost:5003/api/v1/status");

        try {

            log.info("calling stripe paymentIntent endpoint with {}", form);

            String stripeCreatePayentIntentApi = cacheService.getConfigFromCache(StripeConstants.STRIPE_CREATE_PAYMENTINTENT_URI);


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
