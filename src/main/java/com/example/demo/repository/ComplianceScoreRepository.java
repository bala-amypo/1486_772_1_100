package com.example.demo.repository;

import com.example.demo.model.ComplianceScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComplianceScoreRepository extends JpaRepository<ComplianceScore, Long> {

    // ✅ METHOD NAME MUST MATCH EXACTLY
    Optional<ComplianceScore> findByVendor_Id(long vendorId);
}
