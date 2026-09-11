package com.otarjojishvili.settlementfundingselector.controller;

import com.otarjojishvili.settlementfundingselector.dto.SettlementFundingRequest;
import com.otarjojishvili.settlementfundingselector.dto.SettlementFundingResponse;
import com.otarjojishvili.settlementfundingselector.service.SettlementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/settlement")
public class SettlementController {

    private final SettlementService settlementService;

    public SettlementController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    @PostMapping("/fund")
    public ResponseEntity<SettlementFundingResponse> fund(
            @Valid @RequestBody SettlementFundingRequest request) {

        SettlementFundingResponse response = settlementService.fund(request);

        if (response.getSelectedInstructions().isEmpty()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}