package com.orbyte.gateway.dto.carddto;

import com.orbyte.constants.Processor;
import com.orbyte.dto.PaymentRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardProcRequest {
    Processor processor;
    PaymentRequest paymentRequest;
}
