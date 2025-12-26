package com.example.demo.service.impl;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.DocumentType;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.service.DocumentTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository repository;

    public DocumentTypeServiceImpl(DocumentTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public DocumentType create(DocumentType type) {
        return repository.save(type);
    }

    @Override
    public List<DocumentType> getRequiredTypes() {
        return repository.findByRequiredTrue();
    }

    @Override
    public DocumentType getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException("DocumentType not found"));
    }
}
