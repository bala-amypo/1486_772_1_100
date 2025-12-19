package com.example.demo.service;

import com.example.demo.model.Vendor;
import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;
import com.example.demo.repository.VendorRepository;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.repository.VendorDocumentRepository;
import org.springframework.stereotype.Service;

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
        Vendor vendor = vendorRepository.findById(vendorId).orElse(null);
        DocumentType type = documentTypeRepository.findById(typeId).orElse(null);

        document.setVendor(vendor);
        document.setDocumentType(type);
        return vendorDocumentRepository.save(document);
    }

    @Override
    public VendorDocument getDocument(Long id) {
        return vendorDocumentRepository.findById(id).orElse(null);
    }

    @Override
    public List<VendorDocument> getDocumentsByVendor(Long vendorId) {
        return vendorDocumentRepository.findByVendor_Id(vendorId);
    }
}
