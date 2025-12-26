package com.example.demo.controller;

import com.example.demo.model.ComplianceScore;
import com.example.demo.service.ComplianceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compliance-scores")
public class ComplianceScoreController {

    private final ComplianceService complianceService;

    public ComplianceScoreController(ComplianceService complianceService) {
        this.complianceService = complianceService;
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<ComplianceScore> getComplianceScore(
            @PathVariable Long vendorId) {

        return ResponseEntity.ok(
                complianceService.getComplianceScore(vendorId)
        );
    }
}
