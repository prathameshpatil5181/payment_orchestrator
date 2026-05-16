package com.orbyte.orchestrator.exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class CardTransactionFailedException
        extends RuntimeException {

    private final String code;
    private final String subCode;
    private final String txnId;
    private final String txnStatus;
    private final String txnSubStatus;
    private final String processor;
    private final HttpStatusCode httpStatusCode;

    public CardTransactionFailedException(
            String message,
            String code,
            String subCode,
            String txnId,
            String txnStatus,
            String txnSubStatus,
            String processor,
            HttpStatusCode httpStatusCode
    ) {

        super(message);

        this.code = code;
        this.subCode = subCode;
        this.txnId = txnId;
        this.txnStatus = txnStatus;
        this.txnSubStatus = txnSubStatus;
        this.processor = processor;
        this.httpStatusCode = httpStatusCode;
    }
}