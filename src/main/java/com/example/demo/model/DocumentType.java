package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "document_types",
        uniqueConstraints = @UniqueConstraint(columnNames = "type_name")
)
public class DocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_name", nullable = false, unique = true)
    private String typeName;

    private String description;

    @Column(nullable = false)
    private Boolean required;

    @Column(nullable = false)
    private Integer weight;

    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "supportedDocumentTypes")
    private Set<Vendor> vendors = new HashSet<>();

    public DocumentType() {
    }

    public DocumentType(String typeName, String description, Boolean required, Integer weight) {
        this.typeName = typeName;
        this.description = description;
        this.required = required;
        this.weight = weight;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();

        if (this.required == null) {
            this.required = false;
        }

        if (this.weight == null || this.weight < 0) {
            this.weight = 0;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(Set<Vendor> vendors) {
        this.vendors = vendors;
    }
}
