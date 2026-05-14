package com.orbyte.orchestrator.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class CardDto {
    private String brand;

    private Map<String,Object> checks;

    private String country;

//    @JsonProperty("display_brand")
    private String displayBrand;

//    @JsonProperty("exp_month")
    private Integer expMonth;

//    @JsonProperty("exp_year")
    private Integer expYear;

    private String fingerprint;
    private String funding;

//    @JsonProperty("generated_from")
    private String generatedFrom;

    private String last4;

    private Map<String,Object> networks;

//    @JsonProperty("regulated_status")
    private String regulatedStatus;

//    @JsonProperty("three_d_secure_usage")
    private Map<String,Object> threeDSecureUsage;

    private String wallet;
}
