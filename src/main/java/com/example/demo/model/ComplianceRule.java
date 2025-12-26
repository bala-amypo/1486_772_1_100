
package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "compliance_rules")
public class ComplianceRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruleName;

    private Double score;

    @PrePersist
    public void prePersist() {
        if (score == null) {
            score = 0.0;
        }
    }

    public ComplianceRule() {}

    // getters & setters
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
}
