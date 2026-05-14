package com.orbyte.orchestrator.cache;

import com.orbyte.orchestrator.entity.Txn;
import org.springframework.stereotype.Component;


@Component
public class TransactionCacheService {


    public Txn saveTransaction(Txn txn){
        return new Txn();
    }

}
