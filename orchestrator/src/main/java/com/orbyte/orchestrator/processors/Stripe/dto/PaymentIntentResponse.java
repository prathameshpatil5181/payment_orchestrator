package com.orbyte.orchestrator.processors.Stripe.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentIntentResponse {

        @JsonProperty("id")
        private String id;

        @JsonProperty("object")
        private String object;

        @JsonProperty("amount")
        private Integer amount;

        @JsonProperty("amount_capturable")
        private Integer amountCapturable;

        @JsonProperty("amount_details")
        private Map<String,Object> amountDetails;

        @JsonProperty("amount_received")
        private Integer amountReceived;

        @JsonProperty("application")
        private String application;

        @JsonProperty("application_fee_amount")
        private Integer applicationFeeAmount;

        @JsonProperty("automatic_payment_methods")
        private Map<String,Object> automaticPaymentMethods;

        @JsonProperty("canceled_at")
        private Integer canceledAt;

        @JsonProperty("cancellation_reason")
        private String cancellationReason;

        @JsonProperty("capture_method")
        private String captureMethod;

        @JsonProperty("client_secret")
        private String clientSecret;

        @JsonProperty("confirmation_method")
        private String confirmationMethod;

        @JsonProperty("created")
        private Integer created;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("customer")
        private String customer;

        @JsonProperty("description")
        private String description;

        @JsonProperty("last_payment_error")
        private String lastPaymentError;

        @JsonProperty("latest_charge")
        private String latestCharge;

        @JsonProperty("livemode")
        private Boolean livemode;

        @JsonProperty("metadata")
        private Map<String, Object> metadata;

        @JsonProperty("next_action")
        private String nextAction;

        @JsonProperty("on_behalf_of")
        private String onBehalfOf;

        @JsonProperty("payment_method")
        private String paymentMethod;

        @JsonProperty("payment_method_options")
        private Map<String,Object> paymentMethodOptions;

        @JsonProperty("payment_method_types")
        private List<String> paymentMethodTypes;

        @JsonProperty("processing")
        private String processing;

        @JsonProperty("receipt_email")
        private String receiptEmail;

        @JsonProperty("review")
        private String review;

        @JsonProperty("setup_future_usage")
        private String setupFutureUsage;

        @JsonProperty("shipping")
        private String shipping;

        @JsonProperty("source")
        private String source;

        @JsonProperty("statement_descriptor")
        private String statementDescriptor;

        @JsonProperty("statement_descriptor_suffix")
        private String statementDescriptorSuffix;

        @JsonProperty("status")
        private String status;

        @JsonProperty("transfer_data")
        private String transferData;

        @JsonProperty("transfer_group")
        private String transferGroup;


}
