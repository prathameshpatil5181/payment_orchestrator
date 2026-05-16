package com.orbyte.gateway.service.paymentHandlerImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.orbyte.constants.PaymentType;
import com.orbyte.constants.Processor;
import com.orbyte.dto.PaymentRequest;
import com.orbyte.dto.paymentTypeDtos.CardPaymentDetails;
import com.orbyte.dto.paymentTypeDtos.PaymentMethodDetails;
import com.orbyte.gateway.dto.carddto.CardPaymentDto;
import com.orbyte.gateway.dto.carddto.CardProcRequest;
import com.orbyte.gateway.dto.carddto.OrchCardErrorResponseDto;
import com.orbyte.gateway.exception.CardTransactionFailedException;
import com.orbyte.gateway.service.PaymentHandler;
import com.orbyte.utils.Utility;
import io.netty.util.internal.StringUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;


@Service
@Slf4j
public class CardProcessorService implements PaymentHandler  {


    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public CardProcessorService(@LoadBalanced RestClient.Builder restClientbuilder, ObjectMapper objectMapper) {
        this.restClient = restClientbuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public PaymentType supportedPaymentType() {
        return PaymentType.CARD;
    }

    @Override
    public Object process(@NonNull  String transactionId, @NonNull  PaymentRequest paymentRequest) {
    // method processing
        log.info("Inside CardProcessorService.process");
        //validate payment card
      CardPaymentDetails cardPaymentDetails = cardDetailValidator(paymentRequest.getPaymentMethodDetails());

        CardPaymentDto cardPaymentDto = CardPaymentDto.builder().amount(paymentRequest.getAmount()).currency(paymentRequest.getCurrency()).paymentType(String.valueOf(paymentRequest.getPaymentType()).toLowerCase()).token(cardPaymentDetails.getCardToken()).build();

        CardProcRequest cardProcRequest = CardProcRequest.builder().processor(Processor.STRIPE).paymentRequest(paymentRequest).build();

        try{
            return restClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("orchestrator")
                            .path("/orbyte/orchestrator/api/v1/card")
                            .queryParam("txnId", transactionId)
                            .build())
                    .body(cardProcRequest)
                    .retrieve()
                    .body(String.class);
        }
        catch (HttpClientErrorException ex){
            log.error("Client Error {}",ex.getResponseBodyAsString());

            JsonNode error  = Utility.parseJson(ex.getResponseBodyAsString());

            /**
             *              String message,
             *             String code,
             *             String subCode,
             *             String txnId,
             *             String txnStatus,
             *             String txnSubStatus,
             *             String processor,
             *             HttpStatusCode httpStatusCode
             */


            throw new CardTransactionFailedException(error.path("message").asText(),
                    error.path("code").asText(),
                    error.path("subCode").asText(),
                    error.path("txnId").asText(),
                    error.path("txnStatus").asText(),
                    error.path("txnSubStatus").asText(),
                    ex.getStatusCode());
        }
        catch (Exception ex){
            log.error("Server Error {}",ex.getMessage());
            throw new RuntimeException("Failed to processes the txn");
        }

    }

    private CardPaymentDetails cardDetailValidator(PaymentMethodDetails paymentMethodDetails){

        if(paymentMethodDetails instanceof  CardPaymentDetails cardPaymentDetails){
            if (StringUtil.isNullOrEmpty(cardPaymentDetails.getCardToken())) {
                throw new IllegalArgumentException("Invalid card Request parameters");
            }
            else {
                return cardPaymentDetails;
            }
        }
        else{
            throw new IllegalArgumentException("Invalid card Request parameters");
        }

    }

}
