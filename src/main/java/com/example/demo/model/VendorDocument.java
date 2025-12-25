package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class VendorDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vendor vendor;

    @ManyToOne
    private DocumentType documentType;

    private boolean valid;

    public boolean isValid() {      // ✅ REQUIRED
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }
}
