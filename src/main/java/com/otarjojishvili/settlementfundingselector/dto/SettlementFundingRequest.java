package com.otarjojishvili.settlementfundingselector.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public class SettlementFundingRequest {

    @NotNull
    @Positive
    private BigDecimal availableSettlementBalance;

    @NotEmpty
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