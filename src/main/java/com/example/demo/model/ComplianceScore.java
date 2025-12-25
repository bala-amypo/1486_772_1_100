package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ComplianceScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vendor vendor;

    private double score;

    private LocalDate evaluatedAt;

    protected ComplianceScore() {
    }

    public static ComplianceScore create(Vendor vendor) {
        ComplianceScore cs = new ComplianceScore();
        cs.vendor = vendor;
        return cs;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setEvaluatedAt(LocalDate evaluatedAt) {
        this.evaluatedAt = evaluatedAt;
    }
}
