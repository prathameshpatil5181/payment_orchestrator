package com.orbyte.orchestrator.constants;

import org.springframework.stereotype.Component;

@Component
public class AppContants {
    public static final String CONFIG_CACHE_PREFIX = "config::";
    public static final String TOKENIZER_GET_PROCESSOR_TOKEN_URI = "http://tokenizer/orbyte/tokenizer/api/v1/cardtoken/get_processor_token";
}
