package com.otarjojishvili.settlementfundingselector.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class SettlementFundingResponse {

    private UUID requestId;
    private List<InstructionResponse> selectedInstructions;
    private BigDecimal totalSettlementConsumed;
    private BigDecimal totalExpectedFee;
    private OffsetDateTime createdAt;

    public SettlementFundingResponse() {
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public List<InstructionResponse> getSelectedInstructions() {
        return selectedInstructions;
    }

    public void setSelectedInstructions(List<InstructionResponse> selectedInstructions) {
        this.selectedInstructions = selectedInstructions;
    }

    public BigDecimal getTotalSettlementConsumed() {
        return totalSettlementConsumed;
    }

    public void setTotalSettlementConsumed(BigDecimal totalSettlementConsumed) {
        this.totalSettlementConsumed = totalSettlementConsumed;
    }

    public BigDecimal getTotalExpectedFee() {
        return totalExpectedFee;
    }

    public void setTotalExpectedFee(BigDecimal totalExpectedFee) {
        this.totalExpectedFee = totalExpectedFee;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}