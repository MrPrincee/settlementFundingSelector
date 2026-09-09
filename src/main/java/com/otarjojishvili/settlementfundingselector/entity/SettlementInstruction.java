package com.otarjojishvili.settlementfundingselector.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "settlement_instructions")
public class SettlementInstruction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private SettlementRequest request;

    @Column(name = "instruction_reference", nullable = false)
    private String instructionReference;

    @Column(name = "instruction_amount", nullable = false)
    private BigDecimal instructionAmount;

    @Column(name = "expected_fee", nullable = false)
    private BigDecimal expectedFee;

    @Column(nullable = false)
    private boolean selected;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SettlementRequest getRequest() {
        return request;
    }

    public void setRequest(SettlementRequest request) {
        this.request = request;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public SettlementInstruction() {
    }
}