package com.otarjojishvili.settlementfundingselector.algorithm;

import com.otarjojishvili.settlementfundingselector.dto.InstructionRequest;

import java.math.BigDecimal;
import java.util.List;

public class FundingOpt {
    private BigDecimal totalFee;
    private List<InstructionRequest> selectedIns;


    public FundingOpt(BigDecimal totalFee, List<InstructionRequest> selectedIns) {
        this.totalFee = totalFee;
        this.selectedIns = selectedIns;
    }
    public BigDecimal getTotalFee() {
        return totalFee;
    }
    public List<InstructionRequest> getSelectedIns() {
        return selectedIns;
    }


}