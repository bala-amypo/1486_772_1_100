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
    private final DocumentTypeRepository documentTypeRepository;
    private final VendorDocumentRepository vendorDocumentRepository;

    public VendorDocumentServiceImpl(VendorRepository vendorRepository,
                                     DocumentTypeRepository documentTypeRepository,
                                     VendorDocumentRepository vendorDocumentRepository) {
        this.vendorRepository = vendorRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.vendorDocumentRepository = vendorDocumentRepository;
    }

    @Override
    public VendorDocument uploadDocument(Long vendorId, Long typeId, VendorDocument document) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ValidationException("Vendor not found"));

        DocumentType type = documentTypeRepository.findById(typeId)
                .orElseThrow(() -> new ValidationException("Document type not found"));

        if (document.getExpiryDate() != null &&
            document.getExpiryDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Document expired");
        }

        document.setVendor(vendor);
        document.setDocumentType(type);

        return vendorDocumentRepository.save(document);
    }

    @Override
    public VendorDocument getDocument(Long id) {
        return vendorDocumentRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Document not found"));
    }

    @Override
    public List<VendorDocument> getExpiredDocuments() {
        return vendorDocumentRepository.findByExpiryDateBefore(LocalDate.now());
    }
}
