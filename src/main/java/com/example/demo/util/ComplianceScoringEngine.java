package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.Vendor;
import com.example.demo.model.VendorDocument;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ComplianceScoringEngine {

    // Calculate weighted compliance score (0–100)
    public double calculateScore(Vendor vendor, List<DocumentType> requiredTypes, List<VendorDocument> vendorDocuments) {
        if (requiredTypes.isEmpty()) return 100.0;

        double totalWeight = requiredTypes.stream().mapToDouble(DocumentType::getWeight).sum();
        if (totalWeight <= 0) totalWeight = requiredTypes.size(); // fallback if weight=0

        double score = 0.0;

        for (DocumentType type : requiredTypes) {
            boolean valid = vendorDocuments.stream()
                    .anyMatch(d -> d.getDocumentType().getId().equals(type.getId()) && Boolean.TRUE.equals(d.getIsValid()));
            score += valid ? type.getWeight() : 0.0;
        }

        return (score / totalWeight) * 100.0;
    }

    // Derive rating from score
    public String deriveRating(double score) {
        if (score >= 90) return "EXCELLENT";
        if (score >= 70) return "GOOD";
        if (score >= 50) return "POOR";
        return "NONCOMPLIANT";
    }
}
