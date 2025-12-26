package com.example.demo.controller;

import com.example.demo.model.ComplianceScore;
import com.example.demo.service.ComplianceScoreService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance-scores")
public class ComplianceScoreController {

    private final ComplianceScoreService complianceScoreService;

    public ComplianceScoreController(ComplianceScoreService complianceScoreService) {
        this.complianceScoreService = complianceScoreService;
    }

    @PostMapping("/evaluate")
    public ResponseEntity<ComplianceScore> evaluate(
            @RequestParam Long vendorId) {

        return ResponseEntity.ok(
                complianceScoreService.evaluateVendor(vendorId)
        );
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<ComplianceScore> getForVendor(
            @PathVariable Long vendorId) {

        return ResponseEntity.ok(
                complianceScoreService.getScore(vendorId)
        );
    }

    @GetMapping
    public ResponseEntity<List<ComplianceScore>> getAll() {
        return ResponseEntity.ok(
                complianceScoreService.getAllScores()
        );
    }
}
