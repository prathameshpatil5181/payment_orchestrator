package com.orbyte.router.repository;

import com.orbyte.router.entity.RoutingRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface RoutingRulesRepository extends JpaRepository<RoutingRules, Long> {

    @Query("""
        SELECT r FROM RoutingRules r
        WHERE r.active = TRUE
          AND (r.currency         IS NULL OR r.currency         = :currency)
          AND (r.amountMin        IS NULL OR r.amountMin        <= :amount)
          AND (r.amountMax        IS NULL OR r.amountMax        >= :amount)
          AND (r.bin_brand        IS NULL OR r.bin_brand        >= :binBrand)
          AND (r.paymentMethod        IS NULL OR r.paymentMethod        >= :paymentType)
        ORDER BY r.priority ASC
        LIMIT 20
    """)
    public List<RoutingRules> getCandidates(String paymentType, BigInteger amount,String currency,String binBrand );

}