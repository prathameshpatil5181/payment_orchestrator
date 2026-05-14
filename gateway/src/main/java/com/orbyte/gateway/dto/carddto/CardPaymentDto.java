package com.orbyte.gateway.dto.carddto;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.math.BigInteger;

@Data
@Builder
public class CardPaymentDto {
    @NonNull
    private BigInteger amount;
    @NonNull
    private String currency;
    @NonNull
    private String token;
    @NonNull
    private String paymentType;
    // add other address other things like address and other required details that person want to collect
}
