package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.Vendor;
import com.example.demo.model.VendorDocument;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ComplianceScoringEngine {

    private ComplianceScoringEngine() {
    }

    public static double calculateScore(
            Vendor vendor,
            List<VendorDocument> allDocuments,
            List<DocumentType> allTypes
    ) {

        // Filter vendor's uploaded documents
        List<VendorDocument> vendorDocs = allDocuments.stream()
                .filter(d -> d.getVendor().getId().equals(vendor.getId()))
                .filter(d -> d.getExpiryDate() == null ||
                             !d.getExpiryDate().isBefore(LocalDate.now()))
                .toList();

        // Edge case: no required document types
        List<DocumentType> requiredTypes = allTypes.stream()
                .filter(DocumentType::isRequired)
                .toList();

        if (requiredTypes.isEmpty()) {
            return 100.0;
        }

        // Uploaded valid document types
        Set<Long> uploadedTypeIds = vendorDocs.stream()
                .map(d -> d.getDocumentType().getId())
                .collect(Collectors.toSet());

        double totalWeight = requiredTypes.stream()
                .mapToDouble(DocumentType::getWeight)
                .sum();

        double earnedWeight = requiredTypes.stream()
                .filter(t -> uploadedTypeIds.contains(t.getId()))
                .mapToDouble(DocumentType::getWeight)
                .sum();

        if (totalWeight == 0) {
            return 0.0;
        }

        return (earnedWeight / totalWeight) * 100.0;
    }
}
