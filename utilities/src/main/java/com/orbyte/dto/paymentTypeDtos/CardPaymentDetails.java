package com.orbyte.dto.paymentTypeDtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardPaymentDetails implements PaymentMethodDetails {
    @NonNull
    private String cardToken;
}
