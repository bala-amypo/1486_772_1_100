package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.VendorDocument;
import com.example.demo.repository.VendorDocumentRepository;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.repository.VendorRepository;
import com.example.demo.service.VendorDocumentService;

public class VendorDocumentServiceImpl implements VendorDocumentService {

    private final VendorDocumentRepository vendorDocumentRepository;
    private final VendorRepository vendorRepository;
    private final DocumentTypeRepository documentTypeRepository;

    public VendorDocumentServiceImpl(
            VendorDocumentRepository vendorDocumentRepository,
            VendorRepository vendorRepository,
            DocumentTypeRepository documentTypeRepository) {

        this.vendorDocumentRepository = vendorDocumentRepository;
        this.vendorRepository = vendorRepository;
        this.documentTypeRepository = documentTypeRepository;
    }

    @Override
    public VendorDocument uploadDocument(Long vendorId, Long typeId, VendorDocument document) {

        document.setVendor(
                vendorRepository.findById(vendorId)
                        .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"))
        );

        document.setDocumentType(
                documentTypeRepository.findById(typeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Document type not found"))
        );

        return vendorDocumentRepository.save(document);
    }

    @Override
    public VendorDocument getDocument(Long id) {
        return vendorDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));
    }
}
