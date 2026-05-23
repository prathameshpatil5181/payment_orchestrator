package com.orbyte.router.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String code;
}
