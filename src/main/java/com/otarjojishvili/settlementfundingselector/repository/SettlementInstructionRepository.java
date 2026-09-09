package com.otarjojishvili.settlementfundingselector.repository;

import com.otarjojishvili.settlementfundingselector.entity.SettlementInstruction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementInstructionRepository
        extends JpaRepository<SettlementInstruction, Long> {
}