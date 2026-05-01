package com.orbyte.tokenizer.constants;

import org.springframework.stereotype.Component;

@Component
public class TokenizerConstants {
    public final static String STRIPE_SECRET = "stripe_secret_key";
    public final static String STRIPE_PUBLIC_KEY = "stripe_publishable_key";
    public static final String STRIPE_CREATETOKEN_URI = "stripe_token_uri";
    public static final String CONFIG_CACHE_PREFIX = "config::";
}
