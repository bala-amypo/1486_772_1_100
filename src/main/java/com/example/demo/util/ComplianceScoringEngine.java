package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;
import java.util.List;

public class ComplianceScoringEngine {

    public static double calculateScore(
            List<DocumentType> required,
            List<VendorDocument> uploaded) {

        if (required.isEmpty()) {
            return 100.0;
        }

        long matched = required.stream()
            .filter(r ->
                uploaded.stream()
                    .anyMatch(v ->
                        v.getDocumentType().equals(r)
                    )
            ).count();

        return (matched * 100.0) / required.size();
    }

    public static String rating(double score) {
        if (score >= 90) return "EXCELLENT";
        if (score >= 75) return "GOOD";
        if (score >= 50) return "AVERAGE";
        return "POOR";
    }
}
