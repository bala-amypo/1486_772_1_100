package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;

import java.time.LocalDate;
import java.util.List;

public class ComplianceScoringEngine {

    // ✅ Private constructor expected
    private ComplianceScoringEngine() {
    }

    /**
     * ✅ IMPORTANT:
     * Parameter order MUST be:
     * 1) List<VendorDocument>
     * 2) List<DocumentType>
     * 
     * AmyPo tests call this method in THIS order.
     */
    public static double calculateScore(
            List<VendorDocument> documents,
            List<DocumentType> documentTypes) {

        double totalWeight = 0;
        double achievedWeight = 0;

        for (DocumentType type : documentTypes) {

            if (type.getWeight() == null) {
                continue;
            }

            totalWeight += type.getWeight();

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
                achievedWeight += type.getWeight();
            }
        }

        if (totalWeight == 0) {
            return 0;
        }

        return (achievedWeight / totalWeight) * 100;
    }

    /**
     * ✅ Rating logic expected by tests
     */
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
