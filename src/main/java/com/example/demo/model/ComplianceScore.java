package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "compliance_scores")
public class ComplianceScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vendor vendor;

    private double score;

    private String rating;

    public ComplianceScore() {}

    // getters & setters
    public Vendor getVendor() { return vendor; }
    public double getScore() { return score; }
    public String getRating() { return rating; }

    public void setVendor(Vendor vendor) { this.vendor = vendor; }
    public void setScore(double score) { this.score = score; }
    public void setRating(String rating) { this.rating = rating; }
}
