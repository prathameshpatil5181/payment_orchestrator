package com.orbyte.router.controller;

import com.orbyte.router.dto.ErrorResponse;
import com.orbyte.router.exception.NoRoutingRuleFoundException;
import com.orbyte.router.exception.NullRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalErrorHandler(Exception ex){
        ErrorResponse errorResponse = ErrorResponse.builder().message("Inter error occured").code("500").build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(NoRoutingRuleFoundException.class)
    public ResponseEntity<ErrorResponse> noRoutingRuleFoundHandler(NoRoutingRuleFoundException ex){
        ErrorResponse errorResponse = ErrorResponse.builder().message(ex.getMessage()).code("404").build();
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullRequestException.class)
    public ResponseEntity<ErrorResponse> nullRequestHandler(NullRequestException ex){
        ErrorResponse errorResponse = ErrorResponse.builder().message(ex.getMessage()).code("404").build();
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

}
