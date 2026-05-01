package com.orbyte.orchetrator.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class StripePaymentMethodDTO {

    private String id;
    private String object;

//    @JsonProperty("allow_redisplay")
    private String allowRedisplay;

//    @JsonProperty("billing_details")
    private Map<String,String> billingDetails;

    private CardDto card;

    private Long created;

    private String customer;

//    @JsonProperty("customer_account")
    private String customerAccount;

//    @JsonProperty("latest_active_mandate")
    private String latestActiveMandate;

    private Boolean livemode;

    private Map<String, Object> metadata;

//    @JsonProperty("shared_payment_granted_token")
    private String sharedPaymentGrantedToken;

    private String type;
}
