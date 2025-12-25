package com.example.demo.controller;

import com.example.demo.model.ComplianceScore;
import com.example.demo.service.ComplianceScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/compliance-scores")
public class ComplianceScoreController {

    private final ComplianceScoreService scoreService;

    public ComplianceScoreController(ComplianceScoreService scoreService) { this.scoreService = scoreService; }

    @PostMapping("/evaluate")
    public ResponseEntity<ComplianceScore> evaluateVendor(@RequestParam Long vendorId) {
        return ResponseEntity.ok(scoreService.evaluateVendor(vendorId));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<ComplianceScore> getScore(@PathVariable Long vendorId) {
        return ResponseEntity.ok(scoreService.getScore(vendorId));
    }

    @GetMapping
    public ResponseEntity<List<ComplianceScore>> getAllScores() {
        return ResponseEntity.ok(scoreService.getAllScores());
    }
}
