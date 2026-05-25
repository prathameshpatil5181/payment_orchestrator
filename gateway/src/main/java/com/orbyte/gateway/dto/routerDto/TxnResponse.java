package com.orbyte.gateway.dto.routerDto;

import com.orbyte.constants.TxnStatus;
import com.orbyte.constants.TxnSubStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class TxnResponse {
    private UUID txnId;
    private TxnStatus status;
    private TxnSubStatus subStatus;
    private String processor;
    private String description;
    private String procTxnId;
    private String procTxnStatus;
    private String paymentMethod;
    private BigInteger amount;
    private String currency;
    private LocalDateTime createdOn;
    private LocalDateTime txnDate;
    private LocalDateTime updatedOn;
    private Boolean isFailedFirst = false;
}
