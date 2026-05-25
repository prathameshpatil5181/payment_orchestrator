package com.orbyte.gateway.controller;

import com.orbyte.gateway.dto.carddto.CardErrorResponseDto;
import com.orbyte.gateway.dto.dtoimpl.ErrorHandlerDto;
import com.orbyte.gateway.exception.CardTransactionFailedException;
import com.orbyte.gateway.exception.TransactionProcessionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorHandlerDto> globalExceptionHandler(Exception ex){
        ErrorHandlerDto error = ErrorHandlerDto.builder().status("FAILED").subStatus("FAILED").message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CardTransactionFailedException.class)
    public ResponseEntity<CardErrorResponseDto> cardTransactionFailedExceptionHandler(CardTransactionFailedException ex){
        CardErrorResponseDto error =  CardErrorResponseDto.builder().message(ex.getMessage()).code(ex.getCode()).subCode(ex.getSubCode()).txnId(ex.getTxnId()).txnStatus(ex.getTxnStatus()).txnSubStatus(ex.getTxnSubStatus()).build();

        return new ResponseEntity<>(error,ex.getHttpStatusCode());
    }
    @ExceptionHandler(TransactionProcessionException.class)
    public ResponseEntity<ErrorHandlerDto> transactionProcessingException(TransactionProcessionException ex){
        ErrorHandlerDto error = ErrorHandlerDto.builder().message(ex.getMessage()).status("FAILED").build();
        return new ResponseEntity<>(error, HttpStatus.NOT_IMPLEMENTED);
    }
}
