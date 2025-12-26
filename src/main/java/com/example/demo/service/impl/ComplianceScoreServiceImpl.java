package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.ComplianceScoreRepository;
import com.example.demo.util.ComplianceScoringEngine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceScoreServiceImpl {

    private final ComplianceScoreRepository complianceScoreRepository;
    private final ComplianceScoringEngine scoringEngine;

    public ComplianceScoreServiceImpl(
            ComplianceScoreRepository complianceScoreRepository) {
        this.complianceScoreRepository = complianceScoreRepository;
        this.scoringEngine = new ComplianceScoringEngine();
    }

    public ComplianceScore calculateScore(
            Vendor vendor,
            List<DocumentType> documentTypes,
            List<VendorDocument> vendorDocuments) {

        double score = scoringEngine.calculateScore(documentTypes, vendorDocuments);
        String rating = scoringEngine.deriveRating(score);

        ComplianceScore cs = new ComplianceScore();
        cs.setVendor(vendor);
        cs.setScore(score);
        cs.setRating(rating);

        return complianceScoreRepository.save(cs);
    }
}
