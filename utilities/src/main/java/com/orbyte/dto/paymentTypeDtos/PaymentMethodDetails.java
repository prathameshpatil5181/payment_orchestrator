package com.orbyte.dto.paymentTypeDtos;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonSubTypes({
        @JsonSubTypes.Type(value = CardPaymentDetails.class, name = "CARD"),
})
public interface PaymentMethodDetails { }
