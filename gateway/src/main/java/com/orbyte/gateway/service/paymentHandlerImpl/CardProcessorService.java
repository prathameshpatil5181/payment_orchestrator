package com.orbyte.gateway.service.paymentHandlerImpl;

import com.orbyte.constants.PaymentType;
import com.orbyte.constants.Processor;
import com.orbyte.dto.PaymentRequest;
import com.orbyte.dto.paymentTypeDtos.CardPaymentDetails;
import com.orbyte.dto.paymentTypeDtos.PaymentMethodDetails;
import com.orbyte.gateway.dto.carddto.CardPaymentDto;
import com.orbyte.gateway.dto.carddto.CardProcRequest;
import com.orbyte.gateway.service.PaymentHandler;
import io.netty.util.internal.StringUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;



@Service
@Slf4j
public class CardProcessorService implements PaymentHandler  {


    private final RestClient restClient;

    public CardProcessorService(@LoadBalanced RestClient.Builder restClientbuilder) {
        this.restClient = restClientbuilder.build();
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


        Object response = restClient.post().uri("http://orchestrator/orbyte/orchestrator/api/v1/card").body(cardProcRequest).retrieve().body(String.class);

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
