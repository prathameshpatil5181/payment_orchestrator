package com.orbyte.gateway.repository;

import com.orbyte.gateway.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findSessionBySessionId(String sessionId);

    Session findBySessionId(String paymentToken);
}
