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

    private final ComplianceScoringEngine scoringEngine;

    public ComplianceScoreServiceImpl(VendorRepository vendorRepository,
                                      DocumentTypeRepository documentTypeRepository,
                                      VendorDocumentRepository vendorDocumentRepository,
                                      ComplianceScoreRepository complianceScoreRepository,
                                      ComplianceScoringEngine scoringEngine) {
        this.vendorRepository = vendorRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.vendorDocumentRepository = vendorDocumentRepository;
        this.complianceScoreRepository = complianceScoreRepository;
        this.scoringEngine = scoringEngine;
    }

    @Override
    public ComplianceScore evaluateVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        List<DocumentType> requiredTypes = documentTypeRepository.findByRequiredTrue();
        List<VendorDocument> vendorDocuments = vendorDocumentRepository.findByVendor(vendor);

        double score = scoringEngine.calculateScore(vendor, requiredTypes, vendorDocuments);
        if (score < 0) throw new ValidationException("Compliance score cannot be negative");

        String rating = scoringEngine.deriveRating(score);

        ComplianceScore complianceScore = complianceScoreRepository.findByVendorId(vendorId)
                .orElse(new ComplianceScore());

        complianceScore.setVendor(vendor);
        complianceScore.setScoreValue(score);
        complianceScore.setLastEvaluated(LocalDateTime.now());
        complianceScore.setRating(rating);

        return complianceScoreRepository.save(complianceScore);
    }

    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendorId(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("ComplianceScore not found"));
    }

    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
}
