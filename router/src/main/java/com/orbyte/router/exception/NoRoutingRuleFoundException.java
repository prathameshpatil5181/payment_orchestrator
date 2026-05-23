package com.orbyte.router.exception;

public class NoRoutingRuleFoundException extends RuntimeException {
    public NoRoutingRuleFoundException(String message) {
        super(message);
    }
}
