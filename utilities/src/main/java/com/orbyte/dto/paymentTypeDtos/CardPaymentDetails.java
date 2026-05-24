package com.orbyte.dto.paymentTypeDtos;

import com.orbyte.dto.BinInfo;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardPaymentDetails implements PaymentMethodDetails {
    @NonNull
    private String cardToken;
    private BinInfo binInfo ;
}
