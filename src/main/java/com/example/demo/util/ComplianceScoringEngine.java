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

        // No required documents → fully compliant
        if (requiredTypes == null || requiredTypes.isEmpty()) {
            return 100.0;
        }

        // Case 1: Vendor uploaded documents
        if (providedItems != null
                && !providedItems.isEmpty()
                && providedItems.get(0) instanceof VendorDocument) {

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

        // Case 2: Provided document types (weight-based scoring)
        @SuppressWarnings("unchecked")
        List<DocumentType> providedTypes =
                (List<DocumentType>) providedItems;

        int totalWeight = requiredTypes.stream()
                .mapToInt(dt -> dt.getWeight() != null ? dt.getWeight() : 0)
                .sum();

        // Avoid divide-by-zero
        if (totalWeight == 0) {
            return 100.0;
        }

        int providedWeight = providedTypes.stream()
                .mapToInt(dt -> dt.getWeight() != null ? dt.getWeight() : 0)
                .sum();

        return (providedWeight * 100.0) / totalWeight;
    }

    public String deriveRating(double score) {
        if (score >= 90) return "EXCELLENT";
        if (score >= 70) return "GOOD";
        if (score >= 40) return "POOR";
        return "NON_COMPLIANT";
    }
}
