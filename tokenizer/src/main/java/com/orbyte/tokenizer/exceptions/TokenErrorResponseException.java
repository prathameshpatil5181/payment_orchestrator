package com.orbyte.tokenizer.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.bridge.IMessage;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class TokenErrorResponseException extends RuntimeException {

    private final HttpStatus status;

    public TokenErrorResponseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
