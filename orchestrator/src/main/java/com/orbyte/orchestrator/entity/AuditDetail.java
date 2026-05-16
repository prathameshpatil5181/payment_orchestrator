package com.orbyte.orchestrator.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_detail")
public class AuditDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "txn_id", nullable = false, columnDefinition = "UUID")
    private UUID txnId;

    @Column(name = "processor", nullable = false, length = 100)
    private String processor;

    @Column(name = "method", nullable = false, length = 100)
    private String method;

    @Column(name = "type", nullable = false)
    private String type;


    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "data", nullable = false, columnDefinition = "jsonb")
    private String data;

    @Column(name = "created_on", nullable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdOn;
}
