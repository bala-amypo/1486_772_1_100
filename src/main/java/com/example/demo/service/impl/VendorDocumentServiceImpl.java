package com.example.demo.service.impl;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.DocumentType;
import com.example.demo.model.Vendor;
import com.example.demo.model.VendorDocument;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.repository.VendorDocumentRepository;
import com.example.demo.repository.VendorRepository;
import com.example.demo.service.VendorDocumentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VendorDocumentServiceImpl implements VendorDocumentService {

    private final VendorRepository vendorRepository;
    private final DocumentTypeRepository typeRepository;
    private final VendorDocumentRepository documentRepository;

    public VendorDocumentServiceImpl(
            VendorRepository vendorRepository,
            DocumentTypeRepository typeRepository,
            VendorDocumentRepository documentRepository) {

        this.vendorRepository = vendorRepository;
        this.typeRepository = typeRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    public VendorDocument uploadDocument(
            Long vendorId,
            Long typeId,
            VendorDocument document) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ValidationException("Vendor not found"));

        DocumentType type = typeRepository.findById(typeId)
                .orElseThrow(() -> new ValidationException("DocumentType not found"));

        document.setVendor(vendor);
        document.setDocumentType(type);

        return documentRepository.save(document);
    }

    @Override
    public VendorDocument getDocument(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Document not found"));
    }

    @Override
    public List<VendorDocument> getExpiredDocuments() {
        return documentRepository.findByExpiryDateBefore(LocalDate.now());
    }
}
