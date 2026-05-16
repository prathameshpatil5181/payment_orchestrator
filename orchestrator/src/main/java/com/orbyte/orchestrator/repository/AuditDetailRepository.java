package com.orbyte.orchestrator.repository;

import com.orbyte.orchestrator.entity.AuditDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditDetailRepository extends JpaRepository<AuditDetail, Long> {
}