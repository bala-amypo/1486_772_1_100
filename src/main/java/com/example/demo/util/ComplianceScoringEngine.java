package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;

import java.util.List;

public class ComplianceScoringEngine {

    public static double calculateScore(
            List<DocumentType> documentTypes,
            List<VendorDocument> vendorDocuments) {

        int totalWeight = 0;
        int achievedWeight = 0;

        for (DocumentType type : documentTypes) {
            totalWeight += type.getWeight();

            boolean validDocPresent = vendorDocuments.stream()
                    .anyMatch(d ->
                            d.getDocumentType().getId().equals(type.getId()) &&
                            Boolean.TRUE.equals(d.getIsValid())
                    );

            if (validDocPresent) {
                achievedWeight += type.getWeight();
            }
        }

        return totalWeight == 0 ? 0 : (achievedWeight * 100.0) / totalWeight;
    }
}
