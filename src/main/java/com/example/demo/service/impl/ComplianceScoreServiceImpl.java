package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.ComplianceScoreService;
import com.example.demo.util.ComplianceScoringEngine;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplianceScoreServiceImpl implements ComplianceScoreService {

    private final VendorRepository vendorRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final VendorDocumentRepository vendorDocumentRepository;
    private final ComplianceScoreRepository complianceScoreRepository;

    private final ComplianceScoringEngine scoringEngine = new ComplianceScoringEngine();

    // ⚠ Constructor order MUST match tests
    public ComplianceScoreServiceImpl(
            VendorRepository vendorRepository,
            DocumentTypeRepository documentTypeRepository,
            VendorDocumentRepository vendorDocumentRepository,
            ComplianceScoreRepository complianceScoreRepository) {

        this.vendorRepository = vendorRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.vendorDocumentRepository = vendorDocumentRepository;
        this.complianceScoreRepository = complianceScoreRepository;
    }

    @Override
    public ComplianceScore evaluateVendor(Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vendor not found"));

        List<DocumentType> requiredTypes =
                documentTypeRepository.findByRequiredTrue();

        List<VendorDocument> vendorDocs =
                vendorDocumentRepository.findByVendor(vendor);

        double score = scoringEngine.calculateScore(requiredTypes, vendorDocs);

        if (score < 0) {
            throw new ValidationException("Compliance score cannot be negative");
        }

        ComplianceScore complianceScore =
                complianceScoreRepository.findByVendor_Id(vendorId)
                        .orElse(new ComplianceScore());

        complianceScore.setVendor(vendor);
        complianceScore.setScoreValue(score);
        complianceScore.setRating(scoringEngine.deriveRating(score));
        complianceScore.setLastEvaluated(LocalDateTime.now());

        return complianceScoreRepository.save(complianceScore);
    }

    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendor_Id(vendorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Score not found"));
    }

    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
}
