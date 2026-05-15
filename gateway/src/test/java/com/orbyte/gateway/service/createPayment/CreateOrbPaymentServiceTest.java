package com.orbyte.gateway.service.createPayment;

import com.orbyte.gateway.cache.CacheService;
import com.orbyte.gateway.dto.dtoimpl.PaymentInfoDTO;
import com.orbyte.gateway.entity.Session;
import com.orbyte.gateway.repository.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CreateOrbPaymentServiceTest {

    @InjectMocks
    CreateOrbPaymentService createOrbPaymentService;

    @Mock
    SessionRepository sessionRepository;

    @Mock
    CacheService cacheService;

    @Test
    public void createPaymentSessionTest() throws Exception {

        PaymentInfoDTO paymenInfoDTO = PaymentInfoDTO.builder().amount(BigInteger.valueOf(1000)).currency("USD").build();

        when(cacheService.getConfigFromCache("orbyte_secret_key")).thenReturn("test");

        Session session = Session.builder().sessionId("test").amount(paymenInfoDTO.getAmount()).currency(paymenInfoDTO.getCurrency()).build();

        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        String token = createOrbPaymentService.createPaymentSession(paymenInfoDTO);
        log.info("token {}",token);
        Assertions.assertNotNull(token);

    }

}
