package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ComplianceScore;
import com.example.demo.model.DocumentType;
import com.example.demo.model.Vendor;
import com.example.demo.model.VendorDocument;
import com.example.demo.repository.ComplianceScoreRepository;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.repository.VendorDocumentRepository;
import com.example.demo.repository.VendorRepository;
import com.example.demo.service.ComplianceScoreService;
import com.example.demo.util.ComplianceScoringEngine;

import java.time.LocalDateTime;
import java.util.List;

public class ComplianceScoreServiceImpl implements ComplianceScoreService {

    private final VendorRepository vendorRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final VendorDocumentRepository vendorDocumentRepository;
    private final ComplianceScoreRepository complianceScoreRepository;

    private final ComplianceScoringEngine scoringEngine =
            new ComplianceScoringEngine();

    // ⚠️ Constructor order REQUIRED by tests
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

        List<DocumentType> required =
                documentTypeRepository.findByRequiredTrue();

        List<VendorDocument> docs =
                vendorDocumentRepository.findByVendor(vendor);

        double score =
                scoringEngine.calculateScore(required, docs);

        if (score < 0) {
            throw new IllegalArgumentException(
                    "Compliance score cannot be negative");
        }

        ComplianceScore cs =
                complianceScoreRepository
                        .findByVendor_Id(vendorId)
                        .orElse(new ComplianceScore());

        cs.setVendor(vendor);
        cs.setScoreValue(score);
        cs.setRating(scoringEngine.deriveRating(score));
        cs.setLastEvaluated(LocalDateTime.now());

        return complianceScoreRepository.save(cs);
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
