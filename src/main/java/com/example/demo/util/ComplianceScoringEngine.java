package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ComplianceScoringEngine {

    public double calculateScore(
            List<DocumentType> requiredTypes,
            List<?> providedItems) {

        if (requiredTypes == null || requiredTypes.isEmpty()) {
            return 100.0;
        }

        // Case 1: Vendor uploaded documents
        if (providedItems != null &&
            !providedItems.isEmpty() &&
            providedItems.get(0) instanceof VendorDocument) {

            @SuppressWarnings("unchecked")
            List<VendorDocument> vendorDocuments =
                    (List<VendorDocument>) providedItems;

            Set<Long> validUploadedTypeIds = vendorDocuments.stream()
                    .filter(doc ->
                            doc.getExpiryDate() == null ||
                            doc.getExpiryDate().isAfter(LocalDate.now()))
                    .map(doc -> doc.getDocumentType().getId())
                    .collect(Collectors.toSet());

            long uploadedRequired = requiredTypes.stream()
                    .filter(type -> validUploadedTypeIds.contains(type.getId()))
                    .count();

            return (uploadedRequired * 100.0) / requiredTypes.size();

        } 
        // Case 2: Weight-based scoring
        else {

            @SuppressWarnings("unchecked")
            List<DocumentType> providedTypes =
                    (List<DocumentType>) providedItems;

            int totalWeight = requiredTypes.stream()
                    .mapToInt(DocumentType::getWeight)
                    .sum();

            if (totalWeight == 0) {
                return 100.0;
            }

            int providedWeight = providedTypes.stream()
                    .mapToInt(DocumentType::getWeight)
                    .sum();

            return (providedWeight * 100.0) / totalWeight;
        }
    }

    public String deriveRating(double score) {
        if (score >= 90) {
            return "EXCELLENT";
        } else if (score >= 70) {
            return "GOOD";
        } else if (score >= 40) {
            return "POOR";
        } else {
            return "NON_COMPLIANT";
        }
    }
}
