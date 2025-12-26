package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "compliance_rules")
public class ComplianceRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double score;

    @PrePersist
    public void prePersist() {
        if (score == null) {
            score = 0.0;
        }
    }

    public Long getId() { return id; }
    public Double getScore() { return score; }

    public void setId(Long id) { this.id = id; }
    public void setScore(Double score) { this.score = score; }
}
