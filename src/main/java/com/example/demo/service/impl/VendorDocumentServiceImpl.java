package com.example.demo.service.impl;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.VendorDocument;
import com.example.demo.repository.VendorDocumentRepository;
import com.example.demo.service.VendorDocumentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VendorDocumentServiceImpl implements VendorDocumentService {

    private final VendorDocumentRepository vendorDocumentRepository;

    public VendorDocumentServiceImpl(VendorDocumentRepository vendorDocumentRepository) {
        this.vendorDocumentRepository = vendorDocumentRepository;
    }

    // ✅ SIGNATURE MUST MATCH INTERFACE EXACTLY
    @Override
    public VendorDocument uploadDocument(VendorDocument document) {

        if (document.getFileUrl() == null || document.getFileUrl().isBlank()) {
            throw new ValidationException("File URL is required");
        }

        if (document.getExpiryDate() != null &&
                document.getExpiryDate().isBefore(LocalDate.now())) {
            document.setValid(false);
        } else {
            document.setValid(true);
        }

        document.prePersist();
        return vendorDocumentRepository.save(document);
    }
}
