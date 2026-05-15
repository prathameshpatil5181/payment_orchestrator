package com.orbyte.orchestrator.cache;

import com.orbyte.orchestrator.entity.Txn;
import com.orbyte.orchestrator.repository.TxnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TransactionCacheService {


    private final TxnRepository txnRepository;


    @CachePut(cacheNames = "Txn", key = "#result.txnId", unless = "#result.txnId!= null || #result!=null")
    public Txn saveTransaction(Txn txn){
        return txnRepository.save(txn);
    }

}
