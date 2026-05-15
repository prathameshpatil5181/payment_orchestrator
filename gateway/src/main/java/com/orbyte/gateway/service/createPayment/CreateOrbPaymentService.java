package com.orbyte.gateway.service.createPayment;

import com.orbyte.dto.PaymentKeyDTO;
import com.orbyte.gateway.cache.CacheService;
import com.orbyte.gateway.cache.paymentcache.PaymentCacheService;
import com.orbyte.gateway.constants.AppContants;
import com.orbyte.gateway.dto.dtoimpl.*;
import com.orbyte.gateway.entity.Session;
import com.orbyte.gateway.exception.PaymentSessionCreationException;
import com.orbyte.gateway.service.RestService;
import com.orbyte.utils.UniqueGenerator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateOrbPaymentService {

    private final CacheService cacheService;
    private final PaymentCacheService paymentCacheService;
    private final RestService restService;

    @Transactional
    public String createPaymentSession(@NonNull PaymentInfoDTO paymenInfoDTO) {

        try {

            CreateTransactionDto createTransactionDto = CreateTransactionDto.builder().amount(paymenInfoDTO.getAmount()).currency(paymenInfoDTO.getCurrency()).build();

            CreateTransactionResponseDto createTransactionResponseDto = restService.postHandler(AppContants.TRANSACTION_ID_CREATE_URI).body(createTransactionDto).retrieve().body(CreateTransactionResponseDto.class);

            if (createTransactionResponseDto==null || createTransactionResponseDto.getTxnId() == null) throw new PaymentSessionCreationException("Error creating the txn id");


            String secret = cacheService.getConfigFromCache("orbyte_secret_key");

            PaymentKeyDTO paymentKey = PaymentKeyDTO.builder().amount(paymenInfoDTO.getAmount()).currency(paymenInfoDTO.getCurrency()).transactionDate(LocalDateTime.now()).secret(secret).build();

            UniqueGenerator generator = new UniqueGenerator();

            String sessionId = generator.generateUniquePaymentKey(paymentKey);
            log.info("generated session id {}", sessionId);

            Session session = Session.builder().sessionId(sessionId).amount(paymenInfoDTO.getAmount()).currency(paymenInfoDTO.getCurrency()).txnId(UUID.fromString(createTransactionResponseDto.getTxnId())).build();
            paymentCacheService.saveSession(session);
            return generatePaymentUrl(sessionId);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Exception while creating the session {}",e.getMessage() );
            throw new PaymentSessionCreationException("Invalid Parameters");
        }

    }

    // generate the transactionToken as well -- future

    private String generatePaymentUrl(@NonNull String sessionId){
        return AppContants.PAYMENT_SESSION_URI_PREFIX + "/"+ sessionId;
    }

    public PaymentSessionResponse getPaymentSessionDetails (@NonNull String sessionId){

        Session session = paymentCacheService.getPaymentSessionDetails(sessionId);

        log.debug("Received session {}", session);

        PaymentSessionResponse paymentSessionResponse = PaymentSessionResponse.builder().amount(session.getAmount()).currency(session.getCurrency()).sessionId(session.getSessionId()).transactionId(String.valueOf(session.getTxnId())).build();

        log.info("Details of transaction {} is  {}", sessionId, paymentSessionResponse);

        return paymentSessionResponse;

    }

    public boolean isValidSession(@NonNull Session requestSession){
        log.info("Details of session is  {}", requestSession.toString());

        Session session = paymentCacheService.getPaymentSessionDetails(requestSession.getSessionId());

        if (session == null || session.getSessionId() ==null) return false;

        LocalDateTime createdOn = session.getCreatedOn();

        LocalDateTime currentTime = LocalDateTime.now();

        Duration duration = Duration.between(currentTime,createdOn);

        long durationInSeconds = 40L;

        // add more validation to check the txn status

        return true;

    }

}
