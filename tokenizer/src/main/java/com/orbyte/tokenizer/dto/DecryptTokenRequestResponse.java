package com.orbyte.tokenizer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DecryptTokenRequestResponse {
    private String password;
    private String token;
    private CardInfo cardInfo;
}
