package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_scores")
public class ComplianceScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "vendor_id", nullable = false, unique = true)
    private Vendor vendor;

    private Double scoreValue;

    private String rating;

    private LocalDateTime lastEvaluated;

    // ✅ MUST be public
    public ComplianceScore() {
    }

    public ComplianceScore(Vendor vendor, Double scoreValue, String rating) {
        this.vendor = vendor;
        this.scoreValue = scoreValue;
        this.rating = rating;
    }

    @PrePersist
    public void prePersist() {
        this.lastEvaluated = LocalDateTime.now();

        if (this.scoreValue == null || this.scoreValue < 0) {
            this.scoreValue = 0.0;
        }

        if (this.rating == null) {
            if (scoreValue >= 90) rating = "EXCELLENT";
            else if (scoreValue >= 70) rating = "GOOD";
            else if (scoreValue >= 50) rating = "AVERAGE";
            else rating = "POOR";
        }
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public Double getScoreValue() {
        return scoreValue;
    }

    public String getRating() {
        return rating;
    }

    public LocalDateTime getLastEvaluated() {
        return lastEvaluated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public void setScoreValue(Double scoreValue) {
        this.scoreValue = scoreValue;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setLastEvaluated(LocalDateTime lastEvaluated) {
        this.lastEvaluated = lastEvaluated;
    }
}
