package com.orbyte.tokenizer.dto.stripe;

import lombok.Data;

import java.util.Map;

@Data
public class StripeCardInfoToken {

    private String id;
    private String object;

    private String addressCity;
    private String addressCountry;
    private String addressLine1;
    private String addressLine1Check;
    private String addressLine2;
    private String addressState;
    private String addressZip;
    private String addressZipCheck;

    private String brand;
    private String country;
    private String cvcCheck;
    private String dynamicLast4;

    private Integer expMonth;
    private Integer expYear;

    private String fingerprint;
    private String funding;
    private String last4;

    private Map<String, Object> metadata;

    private String name;
    private String tokenizationMethod;
    private String wallet;

}
