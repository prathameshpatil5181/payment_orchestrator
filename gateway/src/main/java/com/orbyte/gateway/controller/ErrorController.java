package com.orbyte.gateway.controller;

import com.orbyte.gateway.dto.carddto.CardErrorResponseDto;
import com.orbyte.gateway.dto.dtoimpl.ErrorHandlerDto;
import com.orbyte.gateway.exception.CardTransactionFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorHandlerDto> globalExceptionHandler(Exception ex){
        ErrorHandlerDto error = ErrorHandlerDto.builder().status("FAILED").message(ex.getMessage()).build();
        return ResponseEntity.ok().body(error);
    }

    @ExceptionHandler(CardTransactionFailedException.class)
    public ResponseEntity<CardErrorResponseDto> cardTransactionFailedExceptionHandler(CardTransactionFailedException ex){
        CardErrorResponseDto error =  CardErrorResponseDto.builder().message(ex.getMessage()).code(ex.getCode()).subCode(ex.getSubCode()).txnId(ex.getTxnId()).txnStatus(ex.getTxnStatus()).txnSubStatus(ex.getTxnSubStatus()).build();

        return new ResponseEntity<>(error,ex.getHttpStatusCode());
    }

}
