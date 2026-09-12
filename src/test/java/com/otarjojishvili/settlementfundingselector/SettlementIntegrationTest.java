package com.otarjojishvili.settlementfundingselector;

import com.otarjojishvili.settlementfundingselector.dto.InstructionRequest;
import com.otarjojishvili.settlementfundingselector.dto.SettlementFundingRequest;
import com.otarjojishvili.settlementfundingselector.dto.SettlementFundingResponse;
import com.otarjojishvili.settlementfundingselector.repository.SettlementInstructionRepository;
import com.otarjojishvili.settlementfundingselector.repository.SettlementRequestRepository;
import com.otarjojishvili.settlementfundingselector.service.SettlementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.otarjojishvili.settlementfundingselector.entity.SettlementInstruction;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class SettlementIntegrationTest {

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private SettlementRequestRepository requestRepository;

    @Autowired
    private SettlementInstructionRepository instructionRepository;

    @Test
    void shouldFundAndPersistRequest() {

        SettlementFundingRequest request = new SettlementFundingRequest();
        request.setAvailableSettlementBalance(new BigDecimal("20000"));

        request.setCandidateInstructions(List.of(
                createInstruction("INS-2001", "7000", "150"),
                createInstruction("INS-2002", "9000", "210"),
                createInstruction("INS-2003", "4000", "90"),
                createInstruction("INS-2004", "6000", "130")
        ));

        SettlementFundingResponse response =
                settlementService.fund(request);

        assertEquals(
                0,
                new BigDecimal("20000")
                        .compareTo(response.getTotalSettlementConsumed())
        );

        assertEquals(
                0,
                new BigDecimal("450")
                        .compareTo(response.getTotalExpectedFee())
        );

        assertEquals(3, response.getSelectedInstructions().size());

        assertTrue(
                requestRepository.findById(response.getRequestId()).isPresent()
        );

        List<SettlementInstruction> savedInstructions =
                instructionRepository.findByRequest_Id(response.getRequestId());

        assertEquals(4, savedInstructions.size());

        long selectedCount = savedInstructions.stream()
                .filter(SettlementInstruction::isSelected)
                .count();

        assertEquals(3, selectedCount);
    }

    private InstructionRequest createInstruction(
            String reference,
            String amount,
            String fee) {

        InstructionRequest instruction = new InstructionRequest();
        instruction.setInstructionReference(reference);
        instruction.setInstructionAmount(new BigDecimal(amount));
        instruction.setExpectedFee(new BigDecimal(fee));

        return instruction;
    }
}