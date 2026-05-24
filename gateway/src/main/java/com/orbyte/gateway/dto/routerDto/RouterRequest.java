package com.orbyte.gateway.dto.routerDto;

import com.orbyte.constants.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouterRequest {
    PaymentType paymentType;
    BigInteger amount;
    String currency;
    String binBrand;
}
