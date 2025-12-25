package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
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

    @Override
    public ComplianceScore evaluateVendor(Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        List<DocumentType> documentTypes = documentTypeRepository.findAll();
        List<VendorDocument> vendorDocuments =
                vendorDocumentRepository.findByVendorId(vendorId);

        double totalWeight = 0.0;
        double achievedWeight = 0.0;

        for (DocumentType type : documentTypes) {

            totalWeight += type.getWeight();

            VendorDocument matchedDoc = vendorDocuments.stream()
                    .filter(doc -> doc.getDocumentType().getId().equals(type.getId()))
                    .findFirst()
                    .orElse(null);

            if (matchedDoc != null &&
                matchedDoc.isValid() &&
                matchedDoc.getExpiryDate() != null &&
                !matchedDoc.getExpiryDate().isBefore(LocalDate.now())) {

                achievedWeight += type.getWeight();
            }
        }

        double score = totalWeight == 0 ? 0 : (achievedWeight / totalWeight) * 100;

        String rating;
        if (score >= 80) {
            rating = "HIGH";
        } else if (score >= 50) {
            rating = "MEDIUM";
        } else {
            rating = "LOW";
        }

        ComplianceScore complianceScore =
                complianceScoreRepository.findByVendorId(vendorId)
                        .orElse(new ComplianceScore());

        complianceScore.setVendor(vendor);
        complianceScore.setScore(score);
        complianceScore.setRating(rating);

        return complianceScoreRepository.save(complianceScore);
    }

    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendorId(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance score not found"));
    }

    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
}
