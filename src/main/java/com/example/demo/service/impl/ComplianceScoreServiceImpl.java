package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.ComplianceScoreService;
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
            ComplianceScoreRepository complianceScoreRepository
    ) {
        this.vendorRepository = vendorRepository;
        this.vendorDocumentRepository = vendorDocumentRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.complianceScoreRepository = complianceScoreRepository;
    }

    // ===============================
    // EVALUATE COMPLIANCE SCORE
    // ===============================
    @Override
    public ComplianceScore evaluateVendor(Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        List<DocumentType> documentTypes = documentTypeRepository.findAll();
        List<VendorDocument> vendorDocuments =
                vendorDocumentRepository.findByVendor(vendor);

        double totalWeight = 0;
        double earnedWeight = 0;

        for (DocumentType type : documentTypes) {
            totalWeight += type.getWeight();

            VendorDocument matchedDoc = vendorDocuments.stream()
                    .filter(d -> d.getDocumentType().getId().equals(type.getId()))
                    .findFirst()
                    .orElse(null);

            if (matchedDoc != null && matchedDoc.isValid()) {
                earnedWeight += type.getWeight();
            }
        }

        double score = totalWeight == 0 ? 0 : (earnedWeight / totalWeight) * 100;

        ComplianceScore complianceScore =
                complianceScoreRepository.findByVendor(vendor)
                        .orElse(new ComplianceScore());

        complianceScore.setVendor(vendor);
        complianceScore.setScore(score);
        complianceScore.setEvaluatedAt(LocalDate.now());

        return complianceScoreRepository.save(complianceScore);
    }

    // ===============================
    // GET SCORE BY VENDOR
    // ===============================
    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendorId(vendorId)
                .orElseThrow(() -> new RuntimeException("Compliance score not found"));
    }

    // ===============================
    // GET ALL SCORES
    // ===============================
    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
}
