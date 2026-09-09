package com.otarjojishvili.settlementfundingselector.dto;

import java.math.BigDecimal;

public class InstructionResponse {

    private String instructionReference;
    private BigDecimal instructionAmount;
    private BigDecimal expectedFee;

    public InstructionResponse() {
    }

    public String getInstructionReference() {
        return instructionReference;
    }

    public void setInstructionReference(String instructionReference) {
        this.instructionReference = instructionReference;
    }

    public BigDecimal getInstructionAmount() {
        return instructionAmount;
    }

    public void setInstructionAmount(BigDecimal instructionAmount) {
        this.instructionAmount = instructionAmount;
    }

    public BigDecimal getExpectedFee() {
        return expectedFee;
    }

    public void setExpectedFee(BigDecimal expectedFee) {
        this.expectedFee = expectedFee;
    }
}