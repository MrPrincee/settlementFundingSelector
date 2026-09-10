package com.otarjojishvili.settlementfundingselector.service;

import com.otarjojishvili.settlementfundingselector.algorithm.FundingAlgorithm;
import com.otarjojishvili.settlementfundingselector.dto.InstructionRequest;
import com.otarjojishvili.settlementfundingselector.dto.SettlementFundingRequest;
import com.otarjojishvili.settlementfundingselector.dto.SettlementFundingResponse;
import com.otarjojishvili.settlementfundingselector.repository.SettlementInstructionRepository;
import com.otarjojishvili.settlementfundingselector.repository.SettlementRequestRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SettlementServiceTest {

    @Test
    void shouldFundSettlementAndReturnCorrectTotals() {

        SettlementRequestRepository requestRepository =
                Mockito.mock(SettlementRequestRepository.class);

        SettlementInstructionRepository instructionRepository =
                Mockito.mock(SettlementInstructionRepository.class);

        FundingAlgorithm fundingAlgorithm = new FundingAlgorithm();

        SettlementService service = new SettlementService(
                requestRepository,
                instructionRepository,
                fundingAlgorithm
        );

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

        SettlementFundingRequest request = new SettlementFundingRequest();
        request.setAvailableSettlementBalance(new BigDecimal("20000"));
        request.setCandidateInstructions(
                List.of(ins1, ins2, ins3, ins4)
        );

        SettlementFundingResponse response = service.fund(request);

        assertEquals(
                new BigDecimal("20000"),
                response.getTotalSettlementConsumed()
        );

        assertEquals(
                new BigDecimal("450"),
                response.getTotalExpectedFee()
        );

        assertEquals(3, response.getSelectedInstructions().size());

        verify(requestRepository, times(1)).save(any());

        verify(instructionRepository, times(1)).saveAll(any());
    }
}