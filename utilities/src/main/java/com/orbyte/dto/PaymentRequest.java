package com.orbyte.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.orbyte.constants.PaymentType;
import com.orbyte.dto.paymentTypeDtos.PaymentMethodDetails;
import lombok.Data;

import java.math.BigInteger;

@Data
public class PaymentRequest {
    PaymentType  paymentType;

    BigInteger amount;

    String currency;


    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "paymentType",
            visible=true
    )
    PaymentMethodDetails paymentMethodDetails;
}
