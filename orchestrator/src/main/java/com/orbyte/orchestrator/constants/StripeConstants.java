package com.orbyte.orchestrator.constants;


import com.orbyte.constants.PaymentType;
import org.springframework.stereotype.Component;

import java.util.EnumSet;


@Component
public class StripeConstants {
    public final static String CARD_PM = "card";
    public final static String STRIPE_VERSION = "2026-04-22.preview";
    // remove after implementation of db fetch
    public final static String STRIPE_SECRET = "stripe_secret_key";

    public final static String STRIPE_CREATE_PAYMENTMETHOD_URI = "stripe_payment_method_uri";

    public final static String STRIPE_CREATE_PAYMENTINTENT_URI = "stripe_payment_intent_uri";

    public final static EnumSet<PaymentType> STRIPE_SUPPORTED_PAYMENT_METHODS = EnumSet.of(PaymentType.CARD);
}

