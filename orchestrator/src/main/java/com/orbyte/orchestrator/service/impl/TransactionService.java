package com.orbyte.orchestrator.service.impl;

import com.github.f4b6a3.uuid.UuidCreator;
import com.orbyte.orchestrator.cache.TransactionCacheService;
import com.orbyte.constants.TxnStatus;
import com.orbyte.constants.TxnSubStatus;
import com.orbyte.orchestrator.dtos.CreateTransactionDto;
import com.orbyte.orchestrator.dtos.CreateTransactionResponseDto;
import com.orbyte.orchestrator.entity.Txn;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionCacheService transactionCacheService;

    @Transactional
    public CreateTransactionResponseDto createTransactionHandler(@NonNull CreateTransactionDto createTransactionDto){
        log.info("Inside createTransactionHandler");
        UUID txnId = createTransactionId(createTransactionDto.getCurrency());
        Txn txn = Txn.builder().txnId(txnId).status(TxnStatus.CREATE).amount(createTransactionDto.getAmount()).currency(createTransactionDto.getCurrency()).subStatus(TxnSubStatus.SUCCESS).build();
        transactionCacheService.saveTransaction(txn);
        log.debug("txn is {}",txn);
        return CreateTransactionResponseDto.builder().txnId(txn.getTxnId().toString()).status(txn.getStatus()).subStatus(txn.getSubStatus()).build();
    }

    private UUID createTransactionId(String currency){
        return UuidCreator.getTimeOrderedEpoch();
    }
}
