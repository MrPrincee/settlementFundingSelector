package com.otarjojishvili.settlementfundingselector.repository;

import com.otarjojishvili.settlementfundingselector.entity.SettlementInstruction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SettlementInstructionRepository
        extends JpaRepository<SettlementInstruction, Long> {

    List<SettlementInstruction> findByRequest_Id(UUID requestId);
}