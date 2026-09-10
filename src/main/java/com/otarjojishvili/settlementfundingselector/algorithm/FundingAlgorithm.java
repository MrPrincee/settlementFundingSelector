package com.otarjojishvili.settlementfundingselector.algorithm;

import com.otarjojishvili.settlementfundingselector.dto.InstructionRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


@Component
public class FundingAlgorithm {

    public List<InstructionRequest> chooseIns(
            List<InstructionRequest> allIns, BigDecimal balance) {

        Map<BigDecimal, FundingOpt> bestPossFee = new HashMap<>();

        bestPossFee.put(
                BigDecimal.ZERO,
                new FundingOpt(BigDecimal.ZERO, new ArrayList<>())
        );

        for (InstructionRequest instruction : allIns) {
            BigDecimal insAmount = instruction.getInstructionAmount();
            BigDecimal insFee = instruction.getExpectedFee();

            Map<BigDecimal, FundingOpt> newPossFee = new HashMap<>(bestPossFee);

            for (Map.Entry<BigDecimal, FundingOpt> entry : bestPossFee.entrySet()) {

                BigDecimal newAmount = entry.getKey()
                        .add(insAmount)
                        .stripTrailingZeros();

                BigDecimal newFee = entry.getValue().getTotalFee().add(insFee);

                if (newAmount.compareTo(balance) <= 0) {

                    List<InstructionRequest> newSelIns =
                            new ArrayList<>(entry.getValue().getSelectedIns());

                    newSelIns.add(instruction);

                    FundingOpt oldOpt = newPossFee.get(newAmount);

                    if (oldOpt == null ||
                            newFee.compareTo(oldOpt.getTotalFee()) > 0) {

                        newPossFee.put(
                                newAmount,
                                new FundingOpt(newFee, newSelIns)
                        );
                    }
                }
            }

            bestPossFee = newPossFee;
        }

        FundingOpt bestOpt = bestPossFee.get(BigDecimal.ZERO);

        for (FundingOpt opt : bestPossFee.values()) {
            if (opt.getTotalFee().compareTo(bestOpt.getTotalFee()) > 0) {
                bestOpt = opt;
            }
        }

        return bestOpt.getSelectedIns();
    }
}