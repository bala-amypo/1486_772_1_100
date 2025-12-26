package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "document_types")
public class DocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean required;

    private double weight;

    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "supportedDocumentTypes")
    private Set<Vendor> vendors = new HashSet<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public DocumentType() {}

    // getters & setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public boolean isRequired() { return required; }
    public double getWeight() { return weight; }
    public Set<Vendor> getVendors() { return vendors; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setRequired(boolean required) { this.required = required; }
    public void setWeight(double weight) { this.weight = weight; }
}
