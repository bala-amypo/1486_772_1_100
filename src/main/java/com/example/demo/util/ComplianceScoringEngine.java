package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;

import java.util.List;

public class ComplianceScoringEngine {

    // ✅ INSTANCE METHOD (NOT static)
    public double calculateScore(
            List<DocumentType> documentTypes,
            List<VendorDocument> vendorDocuments) {

        int totalWeight = 0;
        int achievedWeight = 0;

        for (DocumentType type : documentTypes) {
            totalWeight += type.getWeight();

            boolean validPresent = vendorDocuments.stream()
                    .anyMatch(d ->
                            d.getDocumentType().getId().equals(type.getId()) &&
                            Boolean.TRUE.equals(d.getIsValid())
                    );

            if (validPresent) {
                achievedWeight += type.getWeight();
            }
        }

        return totalWeight == 0 ? 0 : (achievedWeight * 100.0) / totalWeight;
    }

    // ✅ REQUIRED BY TESTS
    public String deriveRating(double score) {
        if (score >= 80) return "EXCELLENT";
        if (score >= 60) return "GOOD";
        if (score >= 40) return "AVERAGE";
        return "POOR";
    }
}
