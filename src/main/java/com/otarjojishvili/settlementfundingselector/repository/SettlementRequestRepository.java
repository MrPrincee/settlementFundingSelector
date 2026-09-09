package com.otarjojishvili.settlementfundingselector.repository;

import com.otarjojishvili.settlementfundingselector.entity.SettlementRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SettlementRequestRepository
        extends JpaRepository<SettlementRequest, UUID> {
}