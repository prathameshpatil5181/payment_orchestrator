package com.orbyte.tokenizer.controllers;

import com.orbyte.tokenizer.dto.ErrorResponse;
import com.orbyte.tokenizer.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalErrorHandler(Exception ex){
        ErrorResponse errorResponse = ErrorResponse.builder().message("Internal error occured").code("500").build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidCardDetailsExecption.class)
    public ResponseEntity<ErrorResponse> invalidCardDetailExceptionHandler(InvalidCardDetailsExecption ex){
        ErrorResponse errorResponse = ErrorResponse.builder().message(ex.getMessage()).code("403").build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PanDecryptionException.class)
    public ResponseEntity<ErrorResponse> panDecryptionExceptionHandler(PanDecryptionException ex){
        ErrorResponse errorResponse = ErrorResponse.builder().message("Could not decrypt pan").code("500").build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PanEncryptionException.class)
    public ResponseEntity<ErrorResponse> panEncryptionExcptionHandler(PanEncryptionException ex){
        ErrorResponse errorResponse = ErrorResponse.builder().message("Internal error occured").code("500").build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BinException.class)
    public ResponseEntity<ErrorResponse> binExceptionHandler(BinException ex){
        ErrorResponse errorResponse = ErrorResponse.builder().message(ex.getMessage()).code("4XX").build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(TokenErrorResponseException.class)
    public ResponseEntity<Object> handleTokenCreationException(TokenErrorResponseException ex) {
        log.error("Token error: {}", ex.getMessage(), ex);

        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("status", ex.getStatus().value());

        return new ResponseEntity<>(body, ex.getStatus());
    }


}
