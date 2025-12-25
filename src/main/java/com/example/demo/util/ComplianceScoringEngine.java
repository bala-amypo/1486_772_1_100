package com.example.demo.util;

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

        return totalWeight == 0 ? 0 : (earnedWeight / totalWeight) * 100;
    }
}
