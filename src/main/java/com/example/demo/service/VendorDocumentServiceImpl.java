package com.example.demo.service;

import com.example.demo.model.Vendor;
import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;
import com.example.demo.repository.VendorRepository;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.repository.VendorDocumentRepository;
import com.example.demo.exceptionhandler.ValidationException;
import com.example.demo.exceptionhandler.ResourceNotFoundException;
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

        // Vendor must exist
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vendor not found"));

        // Document type must exist
        DocumentType type = documentTypeRepository.findById(typeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Document type not found"));

        // Expired document validation
        if (document.getExpiryDate() != null &&
                document.getExpiryDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Document has expired");
        }

        document.setVendor(vendor);
        document.setDocumentType(type);

        return vendorDocumentRepository.save(document);
    }

    @Override
    public VendorDocument getDocument(Long id) {
        return vendorDocumentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Document not found"));
    }

    @Override
    public List<VendorDocument> getDocumentsByVendor(Long vendorId) {
        return vendorDocumentRepository.findByVendor_Id(vendorId);
    }
}
