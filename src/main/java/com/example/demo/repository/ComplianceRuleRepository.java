package com.example.demo.repository;

import com.example.demo.model.ComplianceRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplianceRuleRepository extends JpaRepository<ComplianceRule, Long> {

    List<ComplianceRule> findByDocumentTypeId(Long documentTypeId);
}
