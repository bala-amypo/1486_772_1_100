package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;

import java.time.LocalDate;
import java.util.List;

public class ComplianceScoringEngine {

    // ✅ MUST be public (tests instantiate this class)
    public ComplianceScoringEngine() {
    }

    // ✅ Static scoring method expected by tests
    public static double calculateScore(
            List<DocumentType> documentTypes,
            List<VendorDocument> documents) {

        double totalWeight = 0.0;
        double achievedWeight = 0.0;

        for (DocumentType type : documentTypes) {

            int weight = type.getWeight() == null ? 0 : type.getWeight();
            totalWeight += weight;

            boolean validFound = false;

            for (VendorDocument doc : documents) {

                if (doc.getDocumentType() != null
                        && doc.getDocumentType().getId().equals(type.getId())
                        && Boolean.TRUE.equals(doc.getIsValid())
                        && (doc.getExpiryDate() == null
                        || doc.getExpiryDate().isAfter(LocalDate.now()))) {

                    validFound = true;
                    break;
                }
            }

            if (validFound) {
                achievedWeight += weight;
            }
        }

        if (totalWeight == 0) {
            return 0.0;
        }

        return (achievedWeight / totalWeight) * 100.0;
    }

    // ✅ Rating logic MUST match ComplianceScore expectations
    public static String deriveRating(double score) {

        if (score >= 80) {
            return "HIGH";
        } else if (score >= 50) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }
}
