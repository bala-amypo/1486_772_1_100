package com.example.demo.service;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;

import java.util.List;

public class ComplianceScoringEngine {

    public static double calculateScore(
            List<VendorDocument> vendorDocuments,
            List<DocumentType> documentTypes
    ) {
        double totalWeight = 0;
        double earnedWeight = 0;

        for (DocumentType type : documentTypes) {
            totalWeight += type.getWeight();

            for (VendorDocument doc : vendorDocuments) {
                if (doc.getDocumentType().getId().equals(type.getId())
                        && doc.isValid()) {
                    earnedWeight += type.getWeight();
                    break;
                }
            }
        }

        if (totalWeight == 0) {
            return 0;
        }

        return (earnedWeight / totalWeight) * 100;
    }
}
