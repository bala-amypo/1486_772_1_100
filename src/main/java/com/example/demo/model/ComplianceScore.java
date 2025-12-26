package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ComplianceScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Vendor vendor;

    private double score;

    private LocalDateTime calculatedAt;

    @PrePersist
    public void prePersist() {
        this.calculatedAt = LocalDateTime.now();
    }

    // ===== Getters & Setters =====

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
 
    public double getScore() {
        return score;
    }
 
    public void setScore(double score) {
        this.score = score;
    }
 
    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }
}
