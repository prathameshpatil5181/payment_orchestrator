package com.orbyte.orchestrator.controllers;


import com.orbyte.orchestrator.dtos.CreateTransactionDto;
import com.orbyte.orchestrator.dtos.CreateTransactionResponseDto;
import com.orbyte.orchestrator.service.impl.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/txn")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {


    private final TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<CreateTransactionResponseDto> createTransaction(@RequestBody CreateTransactionDto createTransactionDto){
         CreateTransactionResponseDto createTransactionResponseDto = transactionService.createTransactionHandler(createTransactionDto);

         return  ResponseEntity.ok(createTransactionResponseDto);
    }


}
