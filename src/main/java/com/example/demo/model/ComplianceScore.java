package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ComplianceScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Vendor vendor;

    private double score;

    private String rating;

    private LocalDate evaluatedAt;

    protected ComplianceScore() {
        // JPA
    }

    // ---------- getters & setters ----------

    public Long getId() {
        return id;
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

    // 🔥 REQUIRED BY SERVICE & TESTS
    public void setScore(double score) {
        this.score = score;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public LocalDate getEvaluatedAt() {
        return evaluatedAt;
    }

    // 🔥 REQUIRED BY SERVICE & TESTS
    public void setEvaluatedAt(LocalDate evaluatedAt) {
        this.evaluatedAt = evaluatedAt;
    }
}
