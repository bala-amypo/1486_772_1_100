package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;

import java.time.LocalDate;
import java.util.List;

public class ComplianceScoringEngine {

    public static double calculateScore(
            List<DocumentType> requiredTypes,
            List<VendorDocument> documents) {

        if (requiredTypes.isEmpty()) {
            return 100.0;
        }

        int totalWeight = 0;
        int achievedWeight = 0;

        for (DocumentType type : requiredTypes) {
            totalWeight += type.getWeight();

            documents.stream()
                    .filter(d -> d.getDocumentType().getId().equals(type.getId()))
                    .filter(d -> d.getExpiryDate() == null
                            || d.getExpiryDate().isAfter(LocalDate.now()))
                    .findFirst()
                    .ifPresent(d -> achievedWeight += type.getWeight());
        }

        if (totalWeight == 0) {
            return 100.0;
        }

        return (achievedWeight * 100.0) / totalWeight;
    }

    public static String deriveRating(double score) {
        if (score >= 90) return "EXCELLENT";
        if (score >= 70) return "GOOD";
        if (score >= 40) return "POOR";
        return "NONCOMPLIANT";
    }
}
