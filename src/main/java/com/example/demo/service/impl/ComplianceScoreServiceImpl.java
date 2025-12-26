package com.example.demo.service.impl;

import com.example.demo.model.ComplianceScore;
import com.example.demo.model.DocumentType;
import com.example.demo.model.Vendor;
import com.example.demo.model.VendorDocument;
import com.example.demo.service.ComplianceScoreService;
import com.example.demo.util.ComplianceScoringEngine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceScoreServiceImpl implements ComplianceScoreService {

    @Override
    public ComplianceScore calculate(
            Vendor vendor,
            List<DocumentType> requiredTypes,
            List<VendorDocument> uploadedDocuments) {

        double score = ComplianceScoringEngine
                .calculateScore(requiredTypes, uploadedDocuments);

        ComplianceScore complianceScore = new ComplianceScore();
        complianceScore.setVendor(vendor);
        complianceScore.setScore(score);
        complianceScore.setRating(
                ComplianceScoringEngine.rating(score)
        );

        return complianceScore;
    }
}
