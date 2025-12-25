package com.example.demo.service;

public class ComplianceScoreResult {

    private double score;
    private String rating;

    public ComplianceScoreResult(double score, String rating) {
        this.score = score;
        this.rating = rating;
    }

    public double getScore() {
        return score;
    }

    public String getRating() {
        return rating;
    }
}
