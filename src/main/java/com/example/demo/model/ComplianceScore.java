package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_scores")
public class ComplianceScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tests expect OneToOne with unique vendor_id
    @OneToOne
    @JoinColumn(name = "vendor_id", nullable = false, unique = true)
    private Vendor vendor;

    private Double scoreValue;

    private LocalDateTime lastEvaluated;

    private String rating;

    // REQUIRED no-arg constructor
    public ComplianceScore() {
    }

    // REQUIRED parameterized constructor
    public ComplianceScore(Vendor vendor, Double scoreValue, String rating) {
        this.vendor = vendor;
        this.scoreValue = scoreValue;
        this.rating = rating;
    }

    // IMPORTANT: MUST be PUBLIC (tests call this directly)
    @PrePersist
    public void prePersist() {
        this.lastEvaluated = LocalDateTime.now();

        if (this.scoreValue == null || this.scoreValue < 0) {
            this.scoreValue = 0.0;
        }

        if (this.rating == null) {
            if (this.scoreValue >= 80) {
                this.rating = "HIGH";
            } else if (this.scoreValue >= 50) {
                this.rating = "MEDIUM";
            } else {
                this.rating = "LOW";
            }
        }
    }

    // =====================
    // Getters and Setters
    // =====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Double getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(Double scoreValue) {
        this.scoreValue = scoreValue;
    }

    public LocalDateTime getLastEvaluated() {
        return lastEvaluated;
    }

    public void setLastEvaluated(LocalDateTime lastEvaluated) {
        this.lastEvaluated = lastEvaluated;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
