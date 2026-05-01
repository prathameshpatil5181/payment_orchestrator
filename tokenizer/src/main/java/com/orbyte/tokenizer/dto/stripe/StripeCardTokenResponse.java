package com.orbyte.tokenizer.dto.stripe;

import lombok.Data;

import java.math.BigInteger;

@Data
public class StripeCardTokenResponse {

        private String id;
        private String object;
        private StripeCardInfoToken card;
        private String clientIp;
        private BigInteger created;
        private Boolean livemode;
        private String type;
        private Boolean used;
}
