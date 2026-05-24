package com.orbyte.utils;

import com.orbyte.dto.PaymentKeyDTO;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UniqueGeneratorTest {

    private UniqueGenerator uniqueGenerator = new UniqueGenerator();

    @Test
    public void testGenerateUniquePaymentKeyPositive() throws NoSuchAlgorithmException, InvalidKeyException {
        PaymentKeyDTO paymentKey = PaymentKeyDTO.builder().secret("testsecret").amount(BigInteger.valueOf(4000)).currency("USD").transactionDate(LocalDateTime.now()).build();

        Assertions.assertDoesNotThrow(()->uniqueGenerator.generateUniquePaymentKey(paymentKey));

    }
}
