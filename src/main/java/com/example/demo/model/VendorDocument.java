package com.example.demo.model;

import com.example.demo.exception.ValidationException;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vendor_documents")
public class VendorDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vendor vendor;

    @ManyToOne
    private DocumentType documentType;

    private LocalDate expiryDate;

    @PrePersist
    public void validate() {
        if (expiryDate != null && expiryDate.isBefore(LocalDate.now())) {
            throw new ValidationException("Document expired");
        }
    }

    public Long getId() { return id; }
    public Vendor getVendor() { return vendor; }
    public DocumentType getDocumentType() { return documentType; }
    public LocalDate getExpiryDate() { return expiryDate; }

    public void setId(Long id) { this.id = id; }
    public void setVendor(Vendor vendor) { this.vendor = vendor; }
    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
