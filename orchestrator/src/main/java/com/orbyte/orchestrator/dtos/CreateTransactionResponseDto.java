package com.orbyte.orchestrator.dtos;

import com.orbyte.constants.TxnStatus;
import com.orbyte.constants.TxnSubStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionResponseDto {
    private String txnId;
    private TxnStatus status;
    private TxnSubStatus subStatus;
}
