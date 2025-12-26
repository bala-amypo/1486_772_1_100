package com.example.demo.service.impl;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.VendorDocumentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VendorDocumentServiceImpl implements VendorDocumentService {

    private final VendorRepository vendorRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final VendorDocumentRepository vendorDocumentRepository;

    public VendorDocumentServiceImpl(
            VendorRepository vendorRepository,
            DocumentTypeRepository documentTypeRepository,
            VendorDocumentRepository vendorDocumentRepository) {

        this.vendorRepository = vendorRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.vendorDocumentRepository = vendorDocumentRepository;
    }

    @Override
    public VendorDocument uploadDocument(
            Long vendorId,
            Long typeId,
            VendorDocument document) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        DocumentType type = documentTypeRepository.findById(typeId)
                .orElseThrow(() -> new RuntimeException("Document type not found"));

        if (document.getFileUrl() == null || document.getFileUrl().isBlank()) {
            throw new ValidationException("File URL is required");
        }

        document.setVendor(vendor);
        document.setDocumentType(type);

        if (document.getExpiryDate() != null &&
                document.getExpiryDate().isBefore(LocalDate.now())) {
            document.setValid(false);
        } else {
            document.setValid(true);
        }

        document.prePersist();
        return vendorDocumentRepository.save(document);
    }

    @Override
    public List<VendorDocument> getDocumentsForVendor(Long vendorId) {
        return vendorDocumentRepository.findByVendor_Id(vendorId);
    }

    @Override
    public VendorDocument getDocument(Long documentId) {
        return vendorDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }
}
