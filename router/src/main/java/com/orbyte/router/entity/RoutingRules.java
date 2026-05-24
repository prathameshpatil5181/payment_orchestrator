package com.orbyte.router.entity;

import com.orbyte.constants.Processor;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigInteger;

@Entity
@Table(name = "routing_rules")
@Data
@ToString
public class RoutingRules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String  name;
    private int     priority;

    // Normalised condition columns
    private String  currency;
    private String  cardType;
    private String  country;
    private String  merchantCategory;
    private BigInteger amountMin;
    private BigInteger amountMax;
    private Boolean isRecurring;
    private String  channel;
    private String paymentMethod;
    private String bin_brand;

    // Overflow for genuinely complex conditions
    private String  extraExpression;    // null for most rules

    // Action
    @Enumerated(EnumType.STRING)
    private Processor processor;

    @Enumerated(EnumType.STRING)
    private Processor fallbackProcessors;

    private boolean active;
}