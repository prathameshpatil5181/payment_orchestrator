package com.orbyte.orchestrator.entity;

import com.orbyte.constants.ModifiedBy;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "txn")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Txn {

    @Id
    @Column(name = "txn_id", nullable = false, updatable = false)
    private UUID txnId;

    @Column(name = "status", nullable = false, length = 100)
    private String status;

    @Column(name = "sub_status", nullable = false, length = 100)
    private String subStatus;

    @Column(name = "processor", length = 100)
    private String processor;

    @Column(name = "proc_txn_id", length = 255)
    private String procTxnId;

    @Column(name = "proc_txn_status", length = 100)
    private String procTxnStatus;

    @Column(name = "payment_method", nullable = false, length = 100)
    private String paymentMethod;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "updated_on", nullable = false)
    @CreationTimestamp
    private LocalDateTime updatedOn;

}
