package com.orbyte.tokenizer.constants;

import org.springframework.stereotype.Component;

@Component
public class TokenizerConstants {
    public final static String STRIPE_SECRET = "stripe_secret_key";
    public final static String STRIPE_PUBLIC_KEY = "stripe_publishable_key";
    public static final String STRIPE_CREATETOKEN_URI = "stripe_token_uri";
    public static final String CONFIG_CACHE_PREFIX = "config::";
    public static final String KEY_GENERATION_ALERT = "This is utility to generate a key. Key should be generated once and store in secure vault. Do not use this again and again to generate the key as decryption will not work then";

    public static final String BIN_LOOKUP_URI = "https://lookup.binlist.net";
}
