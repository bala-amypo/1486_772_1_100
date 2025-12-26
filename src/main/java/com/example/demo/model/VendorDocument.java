package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class VendorDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vendor vendor;

    @ManyToOne
    private DocumentType documentType;

    private LocalDate expiryDate;

    private LocalDateTime uploadedAt;

    @PrePersist
    public void prePersist() {
        this.uploadedAt = LocalDateTime.now();
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
 
    public DocumentType getDocumentType() {
        return documentType;
    }
 
    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
 
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
 
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
 
    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
}
