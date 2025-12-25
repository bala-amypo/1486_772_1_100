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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    public ComplianceScore evaluateVendor(Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        List<DocumentType> documentTypes = documentTypeRepository.findAll();
        List<VendorDocument> documents = vendorDocumentRepository.findByVendorId(vendorId);

        // ✅ CORRECT ORDER (documentTypes FIRST, documents SECOND)
        double score = ComplianceScoringEngine.calculateScore(documentTypes, documents);
        String rating = ComplianceScoringEngine.deriveRating(score);

        ComplianceScore complianceScore = complianceScoreRepository
                .findByVendor_Id(vendorId)
                .orElse(new ComplianceScore());

        complianceScore.setVendor(vendor);
        complianceScore.setScore(score);
        complianceScore.setRating(rating);
        complianceScore.setEvaluatedAt(LocalDate.now());

        return complianceScoreRepository.save(complianceScore);
    }

    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendor_Id(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance score not found"));
    }

    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
}
