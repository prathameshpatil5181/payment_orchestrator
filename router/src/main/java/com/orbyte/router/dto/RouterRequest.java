package com.orbyte.router.dto;

import com.orbyte.constants.PaymentType;
import jakarta.ws.rs.ConstrainedTo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouterRequest {
    PaymentType paymentType;
    BigInteger amount;
    String currency;
    String binBrand;
}
