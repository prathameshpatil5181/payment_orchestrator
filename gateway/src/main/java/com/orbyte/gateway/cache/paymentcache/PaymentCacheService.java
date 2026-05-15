package com.orbyte.gateway.cache.paymentcache;

import com.orbyte.gateway.entity.Session;
import com.orbyte.gateway.exception.PaymentSessionDoesNotExist;
import com.orbyte.gateway.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentCacheService {

    private final SessionRepository sessionRepository;

    @Cacheable(cacheNames = "paymentSession",key="#sessionId", unless = "#result==null")
        public Session getPaymentSessionDetails(String sessionId){

        Session session =  sessionRepository.findBySessionId(sessionId);
        log.info("Session value from cache is {}",session);
        if(session==null) throw new PaymentSessionDoesNotExist();
        return  session;

    }

    @CachePut(
            cacheNames = "paymentSession",
            key = "#result.sessionId",
            unless = "#result == null || #result.sessionId == null"
    )
    public Session saveSession(Session session) {
        Session savedSession = sessionRepository.save(session);
        log.info("Session value is {}", savedSession);
        return savedSession;
    }


}
