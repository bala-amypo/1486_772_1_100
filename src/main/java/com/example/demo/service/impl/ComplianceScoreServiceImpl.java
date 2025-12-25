package com.example.demo.service.impl;

import com.example.demo.model.ComplianceScore;
import com.example.demo.model.DocumentType;
import com.example.demo.model.Vendor;
import com.example.demo.model.VendorDocument;
import com.example.demo.repository.ComplianceScoreRepository;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.repository.VendorDocumentRepository;
import com.example.demo.service.ComplianceScoreService;
import com.example.demo.util.ComplianceScoringEngine;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ComplianceScoreServiceImpl implements ComplianceScoreService {

    private final VendorDocumentRepository vendorDocumentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final ComplianceScoreRepository complianceScoreRepository;

    public ComplianceScoreServiceImpl(
            VendorDocumentRepository vendorDocumentRepository,
            DocumentTypeRepository documentTypeRepository,
            ComplianceScoreRepository complianceScoreRepository
    ) {
        this.vendorDocumentRepository = vendorDocumentRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.complianceScoreRepository = complianceScoreRepository;
    }

    @Override
    public ComplianceScore calculateComplianceScore(Vendor vendor) {

        List<VendorDocument> documents =
                vendorDocumentRepository.findByVendor(vendor);

        List<DocumentType> documentTypes =
                documentTypeRepository.findAll();

        double score = ComplianceScoringEngine.calculateScore(
                documents,
                documentTypes
        );

        ComplianceScore complianceScore = ComplianceScore.create(vendor);
        complianceScore.setScore(score);
        complianceScore.setEvaluatedAt(LocalDate.now());

        return complianceScoreRepository.save(complianceScore);
    }

    // ✅ MISSING METHOD — NOW ADDED
    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
}
