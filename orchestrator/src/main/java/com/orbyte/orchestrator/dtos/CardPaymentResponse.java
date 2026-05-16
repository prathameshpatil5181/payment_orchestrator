package com.orbyte.orchestrator.dtos;

import com.orbyte.constants.TxnStatus;
import com.orbyte.constants.TxnSubStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class CardPaymentResponse {

    private UUID txnId;
    private TxnStatus status;
    private TxnSubStatus subStatus;
    private String description;
    private String paymentMethod;
    private BigInteger amount;
    private String currency;
    private LocalDateTime txnDate;
}
