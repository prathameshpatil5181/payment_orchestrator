package com.orbyte.orchestrator.processors.Stripe;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbyte.constants.Processor;
import com.orbyte.constants.TxnStatus;
import com.orbyte.constants.TxnSubStatus;
import com.orbyte.dto.PaymentRequest;
import com.orbyte.dto.paymentTypeDtos.CardPaymentDetails;
import com.orbyte.dto.paymentTypeDtos.PaymentMethodDetails;
import com.orbyte.orchestrator.dtos.StripeDtos.CardTxnResult;
import com.orbyte.orchestrator.processors.Stripe.dto.PaymentIntentResponse;
import com.orbyte.orchestrator.processors.Stripe.dto.PaymentMethodResponse;
import com.orbyte.orchestrator.service.Card;
import com.orbyte.utils.TimeUtil;
import com.orbyte.utils.Utility;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
@Slf4j
public class StripeCardHandler implements Card {

   private final Stripe stripe;

    @Override
    public Processor getProcessor() {
        return Processor.STRIPE;
    }

    @Override
    public CardTxnResult process(PaymentRequest paymentRequest) {

        CardPaymentDetails cardPaymentDetails = cardDetailValidator(paymentRequest.getPaymentMethodDetails());

        MultiValueMap<String, Object> cardForm = new LinkedMultiValueMap<>();
        cardForm.set("type", "card");
        cardForm.set("card[token]", cardPaymentDetails.getCardToken());

        PaymentMethodResponse paymentMethodResponse  = stripe.createPaymentMethod(cardForm);

        // payment intent api also status based action to be done

        MultiValueMap<String, Object> paymentIntentForm = new LinkedMultiValueMap<>();
        paymentIntentForm.set("amount", paymentRequest.getAmount());
        paymentIntentForm.set("currency", paymentRequest.getCurrency());
        paymentIntentForm.set("payment_method", paymentMethodResponse.getId());
        paymentIntentForm.set("confirm", "true");
        paymentIntentForm.set("return_url","http://localhost:5003/orchestrator/api/v1/status");

        try{
            PaymentIntentResponse paymentIntentResponse = stripe.createPaymentIntent(paymentIntentForm);

            TxnSubStatus subStatus = paymentIntentResponse.getStatus().equals("succeeded") ? TxnSubStatus.SUCCESS: TxnSubStatus.FAILED;

            LocalDateTime txntime = TimeUtil.epochSecondsToIst(paymentIntentResponse.getCreated());

            return CardTxnResult.builder().status(TxnStatus.CAPTURE).subStatus(subStatus).description(paymentIntentResponse.getStatus()).procTxnStatus(paymentIntentResponse.getStatus()).processorTxnId(paymentIntentResponse.getId()).txnDate(txntime).redirect_url(null).httpStatusCode(HttpStatusCode.valueOf(200)).build();

        }catch (HttpClientErrorException ex) {
            log.error("Client error {} ", ex.getResponseBodyAsString());

            JsonNode error = Utility.parseJson(ex.getResponseBodyAsString()).path("error");



            JsonNode paymentIntent = error.path("payment_intent");
            String procTxnId = paymentIntent.path("id").asText();

            LocalDateTime txnDate = TimeUtil.epochSecondsToIst(paymentIntent.path("created").asLong());

            return CardTxnResult.builder().status(TxnStatus.CAPTURE).subStatus(TxnSubStatus.FAILED).description(error.path("message").asText()).procTxnStatus(error.path("code").asText()).processorTxnId(procTxnId).txnDate(txnDate).redirect_url(null).declinedCode(error.path("decline_code").asText()).httpStatusCode(ex.getStatusCode()).build();

        }
        catch (Exception parseEx) {

            log.error(
                    "Failed to parse error response",
                    parseEx
            );

            throw new RuntimeException("Failed to parse error response");
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
