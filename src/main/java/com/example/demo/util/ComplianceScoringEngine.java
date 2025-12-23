package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;

import java.time.LocalDate;
import java.util.List;

public class ComplianceScoringEngine {

    private ComplianceScoringEngine() {}

    public static double calculateScore(
            List<DocumentType> documentTypes,
            List<VendorDocument> documents) {

        double totalWeight = 0;
        double achievedWeight = 0;

        for (DocumentType type : documentTypes) {

            totalWeight += type.getWeight();

            boolean validFound = false;

            for (VendorDocument doc : documents) {

                if (doc.getDocumentType().getId().equals(type.getId())
                        && doc.getIsValid()
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

        if (totalWeight == 0) return 0;
        return (achievedWeight / totalWeight) * 100;
    }

    public static String deriveRating(double score) {

        if (score >= 90) return "EXCELLENT";
        if (score >= 70) return "GOOD";
        if (score >= 50) return "AVERAGE";
        return "POOR";
    }
}
