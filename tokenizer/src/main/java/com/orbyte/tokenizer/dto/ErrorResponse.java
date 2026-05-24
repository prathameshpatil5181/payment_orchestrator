package com.orbyte.tokenizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String code;
}
