package com.example.demo.service;

import com.example.demo.model.ComplianceScore;
import com.example.demo.model.Vendor;

import java.util.List;

public interface ComplianceScoreService {

    ComplianceScore calculateComplianceScore(Vendor vendor);

    List<ComplianceScore> getAllScores();   // ✅ MUST be implemented
}
