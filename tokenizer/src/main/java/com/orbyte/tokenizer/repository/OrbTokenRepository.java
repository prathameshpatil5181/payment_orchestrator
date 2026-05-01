package com.orbyte.tokenizer.repository;

import com.orbyte.tokenizer.entity.OrbToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface OrbTokenRepository extends JpaRepository<OrbToken, BigInteger> {
}