package com.example.demo.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "vendor_document_types",
        joinColumns = @JoinColumn(name = "vendor_id"),
        inverseJoinColumns = @JoinColumn(name = "document_type_id")
    )
    private Set<DocumentType> supportedDocumentTypes = new HashSet<>();

    @PrePersist
    public void prePersist() {
        if (supportedDocumentTypes == null) {
            supportedDocumentTypes = new HashSet<>();
        }
    }

    public void addDocumentType(DocumentType type) {
        supportedDocumentTypes.add(type);
        type.getVendors().add(this);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Set<DocumentType> getSupportedDocumentTypes() {
        return supportedDocumentTypes;
    }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}
