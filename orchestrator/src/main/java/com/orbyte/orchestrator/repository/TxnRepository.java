package com.orbyte.orchestrator.repository;

import com.orbyte.orchestrator.entity.Txn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface TxnRepository extends JpaRepository<Txn, String> {
    @Query(value = "SELECT * FROM Txn tx WHERE tx.txn_id=:txnId",nativeQuery = true)
    Txn findByTxnId(UUID txnId);

//    @Modifying
//    @Transactional
//    @Query("UPDATE Txn t SET")
//    int updateTxnDetailsWithTxnId()
}