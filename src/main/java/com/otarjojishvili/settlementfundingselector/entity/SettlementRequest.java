package com.otarjojishvili.settlementfundingselector.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name="settlement_requests")
public class SettlementRequest {

    @Id
    private UUID id;

    @Column(name = "available_balance",nullable=false)
    private BigDecimal availableBalance;

    @Column(name="total_settlement_consumed",nullable=false)
    private BigDecimal totalSettlementConsumed;

    @Column(name = "total_expected_fee",nullable=false)
    private BigDecimal totalExpectedFee;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;


    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public SettlementRequest() {

    }

}
