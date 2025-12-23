package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_rules",
       uniqueConstraints = @UniqueConstraint(columnNames = "ruleName"))
public class ComplianceRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ruleName;

    private String ruleDescription;

    private String matchType;

    private Double threshold;

    private LocalDateTime createdAt;

    public ComplianceRule() {
    }

    public ComplianceRule(String ruleName, String ruleDescription, String matchType, Double threshold) {
        this.ruleName = ruleName;
        this.ruleDescription = ruleDescription;
        this.matchType = matchType;
        this.threshold = threshold;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.threshold == null) {
            this.threshold = 0.0;
        }
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public String getMatchType() {
        return matchType;
    }

    public Double getThreshold() {
        return threshold;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }
}
