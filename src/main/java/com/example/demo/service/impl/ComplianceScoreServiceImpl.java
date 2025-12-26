package com.example.demo.service.impl;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.ComplianceScoreService;
import com.example.demo.util.ComplianceScoringEngine;
import org.springframework.stereotype.Service;

@Service
public class ComplianceScoreServiceImpl implements ComplianceScoreService {

    private final VendorRepository vendorRepository;
    private final VendorDocumentRepository vendorDocumentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final ComplianceScoreRepository complianceScoreRepository;

    public ComplianceScoreServiceImpl(
            VendorRepository vendorRepository,
            VendorDocumentRepository vendorDocumentRepository,
            DocumentTypeRepository documentTypeRepository,
            ComplianceScoreRepository complianceScoreRepository) {
        this.vendorRepository = vendorRepository;
        this.vendorDocumentRepository = vendorDocumentRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.complianceScoreRepository = complianceScoreRepository;
    }

    @Override
    public ComplianceScore calculateScore(Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ValidationException("Vendor not found"));

        double score = ComplianceScoringEngine.calculateScore(
                vendor,
                vendorDocumentRepository.findAll(),
                documentTypeRepository.findAll()
        );

        ComplianceScore complianceScore = new ComplianceScore();
        complianceScore.setVendor(vendor);
        complianceScore.setScore(score);
        complianceScore.setRating(resolveRating(score));

        return complianceScoreRepository.save(complianceScore);
    }

    @Override
    public ComplianceScore getScore(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ValidationException("Vendor not found"));

        return complianceScoreRepository.findByVendor(vendor)
                .orElseThrow(() -> new ValidationException("Score not found"));
    }

    private String resolveRating(double score) {
        if (score >= 90) return "EXCELLENT";
        if (score >= 75) return "GOOD";
        if (score >= 50) return "AVERAGE";
        return "POOR";
    }
}
