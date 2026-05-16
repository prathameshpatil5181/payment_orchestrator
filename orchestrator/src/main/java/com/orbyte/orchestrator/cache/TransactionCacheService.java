package com.orbyte.orchestrator.cache;

import com.orbyte.orchestrator.entity.Txn;
import com.orbyte.orchestrator.repository.TxnRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionCacheService {


    private final TxnRepository txnRepository;


    @CachePut(cacheNames = "Txn", key = "#result.txnId", unless = "#result.txnId!= null || #result!=null")
    public Txn saveTransaction(Txn txn){
        return txnRepository.save(txn);
    }

    @Cacheable(
            cacheNames = "Txn",
            key = "#txnId",
            unless = "#result == null"
    )
    public Txn getTxnDetail(UUID txnId){
        log.info("getting txn data for {}",txnId);
        return txnRepository.findByTxnId(txnId);
    }

    @CacheEvict(cacheNames = "Txn", key = "#txnId")
    public void update(UUID txnId) {
    }

}
