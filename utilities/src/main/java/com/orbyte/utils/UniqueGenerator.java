package com.orbyte.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbyte.dto.PaymentKeyDTO;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.util.UUID;

public class UniqueGenerator {


    public String generateUniqueValue() {

        return UUID.randomUUID().toString();

    }

    public String generateUniquePaymentKey(PaymentKeyDTO paymentKey) throws NoSuchAlgorithmException, InvalidKeyException {

        String uuid = UUID.randomUUID().toString();


        long epochMillis = paymentKey.getTransactionDate()
                .atZone(ZoneId.systemDefault()) // Bind to a specific timezone
                .toInstant()                    // Convert to a moment in time (UTC)
                .toEpochMilli();

        String raw = uuid + "|" + paymentKey.getCurrency() + "|" + paymentKey.getAmount() + "|" + "|" + epochMillis;

        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec = new SecretKeySpec(paymentKey.getSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(keySpec);

        byte[] hmacBytes = mac.doFinal(raw.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(hmacBytes);

    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }



}
