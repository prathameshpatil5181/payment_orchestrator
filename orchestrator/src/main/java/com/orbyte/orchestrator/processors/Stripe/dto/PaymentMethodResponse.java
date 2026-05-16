package com.orbyte.orchestrator.processors.Stripe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentMethodResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("allow_redisplay")
    private String allowRedisplay;

    @JsonProperty("billing_details")
    private BillingDetails billingDetails;

    @JsonProperty("created")
    private Integer created;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("latest_active_mandate")
    private Map<String,String> latestActiveMandate;

    @JsonProperty("livemode")
    private Boolean livemode;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    @JsonProperty("type")
    private String type;

    @JsonProperty("us_bank_account")
    private UsBankAccount usBankAccount;

    // Nested DTOs
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BillingDetails {
        @JsonProperty("address")
        private Address address;

        @JsonProperty("email")
        private String email;

        @JsonProperty("name")
        private String name;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("tax_id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String taxId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        @JsonProperty("city")
        private String city;

        @JsonProperty("country")
        private String country;

        @JsonProperty("line1")
        private String line1;

        @JsonProperty("line2")
        private String line2;

        @JsonProperty("postal_code")
        private String postalCode;

        @JsonProperty("state")
        private String state;


    }



    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsBankAccount {
        @JsonProperty("account_holder_type")
        private String accountHolderType;

        @JsonProperty("account_type")
        private String accountType;

        @JsonProperty("bank_name")
        private String bankName;

        @JsonProperty("financial_connections_account")
        private String financialConnectionsAccount;

        @JsonProperty("fingerprint")
        private String fingerprint;

        @JsonProperty("last4")
        private String last4;

        @JsonProperty("networks")
        private Networks networks;

        @JsonProperty("routing_number")
        private String routingNumber;

        @JsonProperty("status_details")
        private Map<String, Object> statusDetails;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Networks {
        @JsonProperty("preferred")
        private String preferred;

        @JsonProperty("supported")
        private List<String> supported;
    }
}
