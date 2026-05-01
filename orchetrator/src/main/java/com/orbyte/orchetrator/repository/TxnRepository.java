package com.orbyte.orchetrator.repository;

import com.orbyte.orchetrator.entity.Txn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TxnRepository extends JpaRepository<Txn, String> {
}