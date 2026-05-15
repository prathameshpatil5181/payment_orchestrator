package com.orbyte.gateway.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppContants {
    public static final String CONFIG_CACHE_PREFIX = "config::";

    public static String PAYMENT_SESSION_URI_PREFIX = "http://localhost:5003";

    public static String TRANSACTION_ID_CREATE_URI = "http://orchestrator/orbyte/orchestrator/api/v1/txn/create";
}
