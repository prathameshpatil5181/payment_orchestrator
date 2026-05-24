package com.orbyte.orchestrator.controllers;

import com.orbyte.orchestrator.dtos.CardErrorResponseDto;
import com.orbyte.orchestrator.dtos.ErrorResponseDto;
import com.orbyte.orchestrator.exceptions.CardTransactionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalError(Exception ex){
        ErrorResponseDto error = ErrorResponseDto.builder().code("0000").status("FAILED").message(ex.getMessage()).build();

        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CardTransactionFailedException.class)
    public ResponseEntity<CardErrorResponseDto> cardTransactionFailedExceptionHandler(CardTransactionFailedException ex){
        CardErrorResponseDto error =  CardErrorResponseDto.builder().message(ex.getMessage()).code(ex.getCode()).subCode(ex.getSubCode()).txnId(ex.getTxnId()).txnStatus(ex.getTxnStatus()).txnSubStatus(ex.getTxnSubStatus()).processor(ex.getProcessor()).build();

        return new ResponseEntity<>(error,ex.getHttpStatusCode());
    }

}
