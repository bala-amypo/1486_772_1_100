package com.example.demo.dto;

public class DocumentTypeDTO {
    private Long id;
    private String typeName;
    private String description;
    private Boolean required;
    private Integer weight;

    public DocumentTypeDTO() {}

    public DocumentTypeDTO(Long id, String typeName, String description, Boolean required, Integer weight) {
        this.id = id;
        this.typeName = typeName;
        this.description = description;
        this.required = required;
        this.weight = weight;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getRequired() { return required; }
    public void setRequired(Boolean required) { this.required = required; }

    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }
}
