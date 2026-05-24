package com.orbyte.orchestrator.entity;

import com.orbyte.constants.TxnStatus;
import com.orbyte.constants.TxnSubStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigInteger;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 100)
    private TxnStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "sub_status", nullable = false, length = 100)
    private TxnSubStatus subStatus;

    @Column(name = "processor", length = 100)
    private String processor;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "proc_txn_id", length = 255)
    private String procTxnId;

    @Column(name = "proc_txn_status", length = 100)
    private String procTxnStatus;

    @Column(name = "payment_method", length = 100)
    private String paymentMethod;

    @Column(name = "amount", nullable = false)
    private BigInteger amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name="txn_date")
    private LocalDateTime txnDate;

    @Column(name = "updated_on", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @Column(name = "is_failed_first")
    private Boolean isFailedFirst = false;

    @PrePersist
    protected void onCreate() {
        if (this.createdOn == null) {
            this.createdOn = LocalDateTime.now();
        }
        if (this.updatedOn == null) {
            this.updatedOn = LocalDateTime.now();
        }
    }

}
