package com.example.demo.service.impl;

import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.service.DocumentTypeService;

public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    public DocumentTypeServiceImpl(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }
}
