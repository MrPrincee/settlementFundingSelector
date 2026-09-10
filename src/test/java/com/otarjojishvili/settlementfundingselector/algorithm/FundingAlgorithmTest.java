package com.otarjojishvili.settlementfundingselector.algorithm;

import com.otarjojishvili.settlementfundingselector.dto.InstructionRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FundingAlgorithmTest {

    @Test
    void shouldChooseBestInstructions() {

        FundingAlgorithm algorithm = new FundingAlgorithm();

        InstructionRequest ins1 = new InstructionRequest();
        ins1.setInstructionReference("INS-2001");
        ins1.setInstructionAmount(new BigDecimal("7000"));
        ins1.setExpectedFee(new BigDecimal("150"));

        InstructionRequest ins2 = new InstructionRequest();
        ins2.setInstructionReference("INS-2002");
        ins2.setInstructionAmount(new BigDecimal("9000"));
        ins2.setExpectedFee(new BigDecimal("210"));

        InstructionRequest ins3 = new InstructionRequest();
        ins3.setInstructionReference("INS-2003");
        ins3.setInstructionAmount(new BigDecimal("4000"));
        ins3.setExpectedFee(new BigDecimal("90"));

        InstructionRequest ins4 = new InstructionRequest();
        ins4.setInstructionReference("INS-2004");
        ins4.setInstructionAmount(new BigDecimal("6000"));
        ins4.setExpectedFee(new BigDecimal("130"));

        List<InstructionRequest> result = algorithm.chooseIns(
                List.of(ins1, ins2, ins3, ins4),
                new BigDecimal("20000")
        );

        assertEquals(3, result.size());

        assertEquals("INS-2001", result.get(0).getInstructionReference());
        assertEquals("INS-2002", result.get(1).getInstructionReference());
        assertEquals("INS-2003", result.get(2).getInstructionReference());
    }

    @Test
    void shouldReturnEmptyListWhenNothingFits() {

        FundingAlgorithm algorithm = new FundingAlgorithm();

        InstructionRequest ins1 = new InstructionRequest();
        ins1.setInstructionReference("INS-1");
        ins1.setInstructionAmount(new BigDecimal("7000"));
        ins1.setExpectedFee(new BigDecimal("150"));

        InstructionRequest ins2 = new InstructionRequest();
        ins2.setInstructionReference("INS-2");
        ins2.setInstructionAmount(new BigDecimal("9000"));
        ins2.setExpectedFee(new BigDecimal("210"));

        List<InstructionRequest> result = algorithm.chooseIns(
                List.of(ins1, ins2),
                new BigDecimal("1000")
        );

        assertEquals(0, result.size());
    }

    @Test
    void shouldChooseHigherFeeInsteadOfUsingMoreBalance() {

        FundingAlgorithm algorithm = new FundingAlgorithm();

        InstructionRequest ins1 = new InstructionRequest();
        ins1.setInstructionReference("INS-A");
        ins1.setInstructionAmount(new BigDecimal("20000"));
        ins1.setExpectedFee(new BigDecimal("450"));

        InstructionRequest ins2 = new InstructionRequest();
        ins2.setInstructionReference("INS-B");
        ins2.setInstructionAmount(new BigDecimal("18000"));
        ins2.setExpectedFee(new BigDecimal("650"));

        List<InstructionRequest> result = algorithm.chooseIns(
                List.of(ins1, ins2),
                new BigDecimal("20000")
        );

        assertEquals(1, result.size());
        assertEquals("INS-B", result.get(0).getInstructionReference());
    }
}