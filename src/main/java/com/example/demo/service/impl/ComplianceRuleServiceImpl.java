package com.example.demo.service.impl


import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.ComplianceRule;
import com.example.demo.repository.ComplianceRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceRuleServiceImpl implements ComplianceRuleService {

    private final ComplianceRuleRepository complianceRuleRepository;

    public ComplianceRuleServiceImpl(ComplianceRuleRepository complianceRuleRepository) {
        this.complianceRuleRepository = complianceRuleRepository;
    }

    @Override
    public ComplianceRule createRule(ComplianceRule rule) {

        if (rule.getRuleName() != null &&
                complianceRuleRepository.findAll().stream()
                        .anyMatch(r -> rule.getRuleName().equals(r.getRuleName()))) {
            throw new ValidationException("Duplicate rule name");
        }

        return complianceRuleRepository.save(rule);
    }

    @Override
    public List<ComplianceRule> getAllRules() {
        return complianceRuleRepository.findAll();
    }

    @Override
    public ComplianceRule getRule(Long id) {
        return complianceRuleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Rule not found"));
    }
}
