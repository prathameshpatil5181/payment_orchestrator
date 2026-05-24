package com.orbyte.tokenizer.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DecryptTokenRequestResponse {
    private String secret;
    private String token;
    private CardInfo cardInfo;
}
