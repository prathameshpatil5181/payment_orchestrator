package com.orbyte.orchestrator.repository;

import com.orbyte.orchestrator.entity.FailoverTxn;
import com.orbyte.orchestrator.entity.Txn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface FailoverTxnRepository extends JpaRepository<FailoverTxn, UUID> {
    @Query(value = "SELECT * FROM Txn tx WHERE tx.txn_id=:failoverTxnId",nativeQuery = true)
    FailoverTxn findByTxnId(UUID failoverTxnId);
}