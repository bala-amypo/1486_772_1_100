package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
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

        if (vendorId == null) {
            throw new ValidationException("Vendor id cannot be null");
        }

        if (typeId == null) {
            throw new ValidationException("Document type id cannot be null");
        }

        if (document == null) {
            throw new ValidationException("Document cannot be null");
        }

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        DocumentType type = documentTypeRepository.findById(typeId)
                .orElseThrow(() -> new ResourceNotFoundException("DocumentType not found"));

        if (document.getFileUrl() == null || document.getFileUrl().trim().isEmpty()) {
            throw new ValidationException("File URL is required");
        }

        if (document.getExpiryDate() != null &&
                document.getExpiryDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Expiry date cannot be in the past");
        }

        document.setVendor(vendor);
        document.setDocumentType(type);

        // expiryDate == today is VALID
        document.setIsValid(
                document.getExpiryDate() == null ||
                !document.getExpiryDate().isBefore(LocalDate.now())
        );

        return vendorDocumentRepository.save(document);
    }

    @Override
    public List<VendorDocument> getDocumentsForVendor(Long vendorId) {

        if (vendorId == null) {
            throw new ValidationException("Vendor id cannot be null");
        }

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        return vendorDocumentRepository.findByVendor(vendor);
    }

    @Override
    public VendorDocument getDocument(Long id) {

        if (id == null) {
            throw new ValidationException("Document id cannot be null");
        }

        return vendorDocumentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("VendorDocument not found"));
    }
}
