package com.otarjojishvili.settlementfundingselector.service;

import com.otarjojishvili.settlementfundingselector.algorithm.FundingAlgorithm;
import com.otarjojishvili.settlementfundingselector.dto.InstructionRequest;
import com.otarjojishvili.settlementfundingselector.dto.InstructionResponse;
import com.otarjojishvili.settlementfundingselector.dto.SettlementFundingRequest;
import com.otarjojishvili.settlementfundingselector.dto.SettlementFundingResponse;
import com.otarjojishvili.settlementfundingselector.entity.SettlementInstruction;
import com.otarjojishvili.settlementfundingselector.entity.SettlementRequest;
import com.otarjojishvili.settlementfundingselector.repository.SettlementInstructionRepository;
import com.otarjojishvili.settlementfundingselector.repository.SettlementRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SettlementService {

    private final SettlementRequestRepository requestRepository;
    private final SettlementInstructionRepository instructionRepository;
    private final FundingAlgorithm fundingAlgorithm;

    public SettlementService(
            SettlementRequestRepository requestRepository,
            SettlementInstructionRepository instructionRepository,
            FundingAlgorithm fundingAlgorithm) {

        this.requestRepository = requestRepository;
        this.instructionRepository = instructionRepository;
        this.fundingAlgorithm = fundingAlgorithm;
    }

    @Transactional
    public SettlementFundingResponse fund(SettlementFundingRequest request) {

        List<InstructionRequest> selectedIns = fundingAlgorithm.chooseIns(
                request.getCandidateInstructions(),
                request.getAvailableSettlementBalance()
        );

        BigDecimal totalConsumed = BigDecimal.ZERO;
        BigDecimal totalFee = BigDecimal.ZERO;

        for (InstructionRequest instruction : selectedIns) {
            totalConsumed = totalConsumed.add(instruction.getInstructionAmount());
            totalFee = totalFee.add(instruction.getExpectedFee());
        }

        SettlementRequest settlementRequest = new SettlementRequest();

        settlementRequest.setId(UUID.randomUUID());
        settlementRequest.setAvailableBalance(
                request.getAvailableSettlementBalance()
        );
        settlementRequest.setTotalSettlementConsumed(totalConsumed);
        settlementRequest.setTotalExpectedFee(totalFee);
        settlementRequest.setCreatedAt(OffsetDateTime.now());

        requestRepository.save(settlementRequest);

        List<SettlementInstruction> instructions = new ArrayList<>();

        for (InstructionRequest instruction : request.getCandidateInstructions()) {

            SettlementInstruction entity = new SettlementInstruction();

            entity.setRequest(settlementRequest);
            entity.setInstructionReference(
                    instruction.getInstructionReference()
            );
            entity.setInstructionAmount(
                    instruction.getInstructionAmount()
            );
            entity.setExpectedFee(
                    instruction.getExpectedFee()
            );
            entity.setSelected(selectedIns.contains(instruction));

            instructions.add(entity);
        }

        instructionRepository.saveAll(instructions);

        List<InstructionResponse> selectedResponses = new ArrayList<>();

        for (InstructionRequest instruction : selectedIns) {

            InstructionResponse response = new InstructionResponse();

            response.setInstructionReference(
                    instruction.getInstructionReference()
            );
            response.setInstructionAmount(
                    instruction.getInstructionAmount()
            );
            response.setExpectedFee(
                    instruction.getExpectedFee()
            );

            selectedResponses.add(response);
        }

        SettlementFundingResponse response = new SettlementFundingResponse();

        response.setRequestId(settlementRequest.getId());
        response.setSelectedInstructions(selectedResponses);
        response.setTotalSettlementConsumed(totalConsumed);
        response.setTotalExpectedFee(totalFee);
        response.setCreatedAt(settlementRequest.getCreatedAt());

        return response;
    }
}