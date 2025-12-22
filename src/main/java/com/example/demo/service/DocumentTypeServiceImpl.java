package com.example.demo.service;

import com.example.demo.model.DocumentType;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.exceptionhandler.ValidationException;
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

    
        if (documentTypeRepository.existsByTypeName(type.getTypeName())) {
            throw new ValidationException("Document Type already exists");
        }

        return documentTypeRepository.save(type);
    }

    @Override
    public DocumentType getDocumentType(Long id) {
        return documentTypeRepository.findById(id).orElse(null);
    }

    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeRepository.findAll();
    }
}
