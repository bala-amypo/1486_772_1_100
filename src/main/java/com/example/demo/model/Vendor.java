package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
        name = "vendor_document_types",
        joinColumns = @JoinColumn(name = "vendor_id"),
        inverseJoinColumns = @JoinColumn(name = "document_type_id")
    )
    private Set<DocumentType> supportedDocumentTypes = new HashSet<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Vendor() {}

    // getters & setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Set<DocumentType> getSupportedDocumentTypes() { return supportedDocumentTypes; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}
