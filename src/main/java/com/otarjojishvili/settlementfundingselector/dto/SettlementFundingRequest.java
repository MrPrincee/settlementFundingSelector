package com.otarjojishvili.settlementfundingselector.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.List;

public class SettlementFundingRequest {

    @NotNull
    @PositiveOrZero
    @Digits(integer = 17, fraction = 2)
    private BigDecimal availableSettlementBalance;

    @NotNull
    @Valid
    private List<InstructionRequest> candidateInstructions;

    public SettlementFundingRequest() {
    }

    public BigDecimal getAvailableSettlementBalance() {
        return availableSettlementBalance;
    }

    public void setAvailableSettlementBalance(BigDecimal availableSettlementBalance) {
        this.availableSettlementBalance = availableSettlementBalance;
    }

    public List<InstructionRequest> getCandidateInstructions() {
        return candidateInstructions;
    }

    public void setCandidateInstructions(List<InstructionRequest> candidateInstructions) {
        this.candidateInstructions = candidateInstructions;
    }
}