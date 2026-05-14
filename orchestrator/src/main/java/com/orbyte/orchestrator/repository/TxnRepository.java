package com.orbyte.orchestrator.repository;

import com.orbyte.orchestrator.entity.Txn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TxnRepository extends JpaRepository<Txn, String> {
}