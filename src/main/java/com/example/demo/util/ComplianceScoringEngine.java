package com.example.demo.util;

import com.example.demo.model.*;

import java.util.List;

public class ComplianceScoringEngine {

    // ✅ TEST EXPECTS THIS SIGNATURE
    public static double calculateScore(
            Vendor vendor,
            List<DocumentType> documentTypes,
            List<VendorDocument> vendorDocuments) {

        int totalWeight = 0;
        int achievedWeight = 0;

        for (DocumentType type : documentTypes) {
            totalWeight += type.getWeight();

            boolean validDocPresent = vendorDocuments.stream()
                    .anyMatch(doc ->
                            doc.getVendor().getId().equals(vendor.getId()) &&
                            doc.getDocumentType().getId().equals(type.getId()) &&
                            doc.getIsValid()
                    );

            if (validDocPresent) {
                achievedWeight += type.getWeight();
            }
        }

        return totalWeight == 0 ? 0 : (achievedWeight * 100.0) / totalWeight;
    }

    public static String deriveRating(double score) {
        if (score >= 80) return "EXCELLENT";
        if (score >= 60) return "GOOD";
        if (score > 0) return "POOR";
        return "NONCOMPLIANT";
    }
}
