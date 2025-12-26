package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ComplianceScoringEngine {

    public int calculateScore(
            List<DocumentType> requiredTypes,
            List<VendorDocument> vendorDocuments) {

        if (requiredTypes == null || requiredTypes.isEmpty()) {
            return 100;
        }

        Set<Long> validUploadedTypeIds = vendorDocuments.stream()
                .filter(doc ->
                        doc.getExpiryDate() == null ||
                        doc.getExpiryDate().isAfter(LocalDate.now()))
                .map(doc -> doc.getDocumentType().getId())
                .collect(Collectors.toSet());

        long uploadedRequired = requiredTypes.stream()
                .filter(type -> validUploadedTypeIds.contains(type.getId()))
                .count();

        return (int) ((uploadedRequired * 100) / requiredTypes.size());
    }

    // ✅ REQUIRED by ComplianceScoreServiceImpl + tests
    public String deriveRating(double score) {
        if (score >= 90) {
            return "EXCELLENT";
        } else if (score >= 70) {
            return "GOOD";
        } else if (score >= 40) {
            return "POOR";
        } else {
            return "NONCOMPLIANT";
        }
    }
}
