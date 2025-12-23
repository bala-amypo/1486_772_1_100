package com.example.demo.util;

import com.example.demo.model.ComplianceRule;
import com.example.demo.model.VendorDocument;

import java.time.LocalDate;
import java.util.List;

public class ComplianceScoringEngine {

    private ComplianceScoringEngine() {
        // Utility class
    }

    public static int calculateScore(
            List<ComplianceRule> rules,
            List<VendorDocument> documents) {

        int totalScore = 0;

        for (ComplianceRule rule : rules) {

            boolean satisfied = false;

            for (VendorDocument doc : documents) {

                if (doc.getDocumentType().getId()
                        .equals(rule.getDocumentType().getId())) {

                    if (doc.getIsValid() &&
                        (doc.getExpiryDate() == null ||
                         doc.getExpiryDate().isAfter(LocalDate.now()))) {

                        satisfied = true;
                        break;
                    }
                }
            }

            if (satisfied) {
                totalScore += rule.getWeight();
            }
        }

        return totalScore;
    }
}
