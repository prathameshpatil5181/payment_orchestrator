package com.orbyte.orchestrator.processors.Stripe;

import com.orbyte.constants.Processor;
import com.orbyte.dto.PaymentRequest;
import com.orbyte.dto.paymentTypeDtos.CardPaymentDetails;
import com.orbyte.dto.paymentTypeDtos.PaymentMethodDetails;
import com.orbyte.orchestrator.service.Card;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class StripeCardHandler implements Card {

   private final Stripe stripe;


    @Override
    public Processor getProcessor() {
        return Processor.STRIPE;
    }

    @Override
    public String process(PaymentRequest paymentRequest) {



        CardPaymentDetails cardPaymentDetails = cardDetailValidator(paymentRequest.getPaymentMethodDetails());

        MultiValueMap<String, Object> cardForm = new LinkedMultiValueMap<>();
        cardForm.set("type", "card");
        cardForm.set("card[token]", cardPaymentDetails.getCardToken());

        String paymentMethodId = stripe.createPaymentMethod(cardForm);

        // payment intent api also status based action to be done

        MultiValueMap<String, Object> paymentIntentForm = new LinkedMultiValueMap<>();
        paymentIntentForm.set("amount", paymentRequest.getAmount());
        paymentIntentForm.set("currency", paymentRequest.getCurrency());
        paymentIntentForm.set("payment_method", paymentMethodId);
        paymentIntentForm.set("confirm", "true");
        paymentIntentForm.set("return_url","http://localhost:5003/orchestrator/api/v1/status");

        String response = stripe.createPaymentIntent(paymentIntentForm);

        return response;

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
