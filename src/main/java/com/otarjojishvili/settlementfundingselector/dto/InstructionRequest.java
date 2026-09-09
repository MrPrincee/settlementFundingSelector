package com.otarjojishvili.settlementfundingselector.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class InstructionRequest {

    @NotBlank
    private String instructionReference;

    @NotNull
    @Positive
    private BigDecimal instructionAmount;

    @NotNull
    @Positive
    private BigDecimal expectedFee;

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

    public InstructionRequest() {
    }

}