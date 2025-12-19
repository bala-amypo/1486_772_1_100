package com.example.demo.service;

import com.example.demo.model.ComplianceScore;
import com.example.demo.model.Vendor;
import com.example.demo.repository.ComplianceScoreRepository;
import com.example.demo.repository.VendorRepository;
import com.example.demo.repository.VendorDocumentRepository;
import com.example.demo.repository.ComplianceRuleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplianceScoreServiceImpl implements ComplianceScoreService {

    private final ComplianceScoreRepository complianceScoreRepository;
    private final VendorRepository vendorRepository;
    private final VendorDocumentRepository vendorDocumentRepository;
    private final ComplianceRuleRepository complianceRuleRepository;

    public ComplianceScoreServiceImpl(
            ComplianceScoreRepository complianceScoreRepository,
            VendorRepository vendorRepository,
            VendorDocumentRepository vendorDocumentRepository,
            ComplianceRuleRepository complianceRuleRepository) {
        this.complianceScoreRepository = complianceScoreRepository;
        this.vendorRepository = vendorRepository;
        this.vendorDocumentRepository = vendorDocumentRepository;
        this.complianceRuleRepository = complianceRuleRepository;
    }

    @Override
    public ComplianceScore evaluateVendor(Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId).orElse(null);
        if (vendor == null) {
            return null; // or throw exception later
        }

        // If score already exists → update
        ComplianceScore score = complianceScoreRepository
                .findByVendor_Id(vendorId)
                .orElse(new ComplianceScore());

        score.setVendor(vendor);
        score.setScoreValue(100.0);      // placeholder logic
        score.setRating("EXCELLENT");    // placeholder logic
        score.setLastEvaluated(LocalDateTime.now());

        return complianceScoreRepository.save(score);
    }

    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendor_Id(vendorId).orElse(null);
    }

    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
}
