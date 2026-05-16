package com.orbyte.orchestrator.service.impl;

import com.orbyte.constants.TxnStatus;
import com.orbyte.orchestrator.cache.TransactionCacheService;
import com.orbyte.orchestrator.dtos.CardPaymentResponse;
import com.orbyte.orchestrator.dtos.CardProcRequest;
import com.orbyte.orchestrator.dtos.StripeDtos.CardTxnResult;
import com.orbyte.orchestrator.entity.Txn;
import com.orbyte.orchestrator.exceptions.CardTransactionFailedException;
import com.orbyte.orchestrator.factory.CardHandlerFactory;
import com.orbyte.orchestrator.repository.TxnRepository;
import com.orbyte.orchestrator.service.Card;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CardService {


    private final CardHandlerFactory cardHandlerFactory;

    private final TransactionCacheService transactionCacheService;

    private final TxnRepository txnRepository;

    @Transactional(
            noRollbackFor = CardTransactionFailedException.class
    )

    public Txn processCardRequest(CardProcRequest cardProcRequest, UUID txnId){
        log.info("inside CardService.processCardRequest with params {} {}",cardProcRequest,txnId );
        Card cardHandler = cardHandlerFactory.getCardProcessor(cardProcRequest.getProcessor());
        Txn txn  = txnRepository.findByTxnId(txnId);
        log.info("txn data is {}",txn );

        if (txn.getStatus() != TxnStatus.CREATE) return txn;

        CardTxnResult cardTxnResult = cardHandler.process(cardProcRequest.getPaymentRequest());

        log.info("CardTxnResult is {}",cardTxnResult.toString());

        txn.setProcessor(String.valueOf(cardProcRequest.getProcessor()));
            txn.setProcTxnId(cardTxnResult.getProcessorTxnId());
            txn.setProcTxnStatus(cardTxnResult.getProcTxnStatus());
            txn.setSubStatus(cardTxnResult.getSubStatus());
            txn.setUpdatedOn(LocalDateTime.now());
            txn.setPaymentMethod("CARD");
            txn.setStatus(cardTxnResult.getStatus());
            txn.setDescription(cardTxnResult.getDescription());
            txn.setTxnDate(cardTxnResult.getTxnDate());


            if(cardTxnResult.getHttpStatusCode()!= HttpStatusCode.valueOf(200)){
                /**
                 *             String message,
                 *             String code,
                 *             String subCode,
                 *             String txnId,
                 *             String txnStatus,
                 *             String txnSubStatus,
                 *             String processor,
                 *             HttpStatusCode httpStatusCode
                 */


                throw new CardTransactionFailedException(
                        cardTxnResult.getDescription(),
                        cardTxnResult.getProcTxnStatus(),
                        cardTxnResult.getDeclinedCode() ,
                        txnId.toString(),
                        cardTxnResult.getStatus().name(),
                        cardTxnResult.getSubStatus().name(),
                        cardProcRequest.getProcessor().name(),
                        cardTxnResult.getHttpStatusCode()

                );
            }


            return  txn;


    }


}
