package com.example.demo.repository;

import com.example.demo.model.ComplianceScore;
import com.example.demo.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComplianceScoreRepository extends JpaRepository<ComplianceScore, Long> {

    Optional<ComplianceScore> findByVendor(Vendor vendor);
}
