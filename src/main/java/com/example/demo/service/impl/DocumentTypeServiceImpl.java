package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.DocumentType;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.service.DocumentTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    public DocumentTypeServiceImpl(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    @Override
    public DocumentType createDocumentType(DocumentType type) {

        if (type == null) {
            throw new ValidationException("DocumentType cannot be null");
        }

        if (type.getTypeName() == null || type.getTypeName().trim().isEmpty()) {
            throw new ValidationException("Document type name is required");
        }

        if (documentTypeRepository.existsByTypeName(type.getTypeName())) {
            throw new ValidationException("Duplicate document type");
        }

        if (type.getWeight() != null && type.getWeight() < 0) {
            throw new ValidationException("Weight cannot be negative");
        }

        return documentTypeRepository.save(type);
    }

    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeRepository.findAll();
    }

    @Override
    public DocumentType getDocumentType(Long id) {

        if (id == null) {
            throw new ValidationException("DocumentType id cannot be null");
        }

        return documentTypeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("DocumentType not found"));
    }
}
