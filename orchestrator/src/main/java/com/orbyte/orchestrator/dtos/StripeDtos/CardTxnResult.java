package com.orbyte.orchestrator.dtos.StripeDtos;

import com.orbyte.constants.TxnStatus;
import com.orbyte.constants.TxnSubStatus;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CardTxnResult {
    private TxnStatus status;
    private TxnSubStatus subStatus;
    private String processorTxnId;
    private String procTxnStatus;
    private String description;
    private LocalDateTime txnDate;
    private String redirect_url;
    private String declinedCode;
    private HttpStatusCode httpStatusCode;
}
