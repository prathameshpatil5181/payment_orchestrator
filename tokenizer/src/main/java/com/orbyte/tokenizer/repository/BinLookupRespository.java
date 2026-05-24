package com.orbyte.tokenizer.repository;

import com.orbyte.tokenizer.dto.BinLookupResponse;
import com.orbyte.tokenizer.entity.BinLookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BinLookupRespository extends JpaRepository<BinLookup, Long> {
    @Query(value = "SELECT * FROM bin_lookup b WHERE b.bin=:Bin",  nativeQuery = true)
    Optional<BinLookup> findByBin(String Bin);
}