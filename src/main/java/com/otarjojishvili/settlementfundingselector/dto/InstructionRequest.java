package com.otarjojishvili.settlementfundingselector.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class InstructionRequest {

    @NotBlank
    private String instructionReference;

    @NotNull
    @Positive
    @Digits(integer = 17, fraction = 2)
    private BigDecimal instructionAmount;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 17, fraction = 2)
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