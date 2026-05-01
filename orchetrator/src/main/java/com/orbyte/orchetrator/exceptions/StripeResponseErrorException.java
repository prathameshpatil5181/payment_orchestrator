package com.orbyte.orchetrator.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StripeResponseErrorException extends RuntimeException {
    private HttpStatus status;
    public StripeResponseErrorException(String message, HttpStatus status) {
        super(message);
    }
}
