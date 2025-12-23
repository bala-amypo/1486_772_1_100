package com.example.demo.service;

import com.example.demo.model.DocumentType;
import com.example.demo.model.Vendor;
import com.example.demo.model.VendorDocument;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.repository.VendorDocumentRepository;
import com.example.demo.repository.VendorRepository;
import com.example.demo.util.ComplianceScoringEngine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceScoreServiceImpl implements ComplianceScoreService {

    private final VendorRepository vendorRepository;
    private final VendorDocumentRepository vendorDocumentRepository;
    private final DocumentTypeRepository documentTypeRepository;

    public ComplianceScoreServiceImpl(
            VendorRepository vendorRepository,
            VendorDocumentRepository vendorDocumentRepository,
            DocumentTypeRepository documentTypeRepository) {

        this.vendorRepository = vendorRepository;
        this.vendorDocumentRepository = vendorDocumentRepository;
        this.documentTypeRepository = documentTypeRepository;
    }

    @Override
    public ComplianceScoreResult calculateScore(Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        List<VendorDocument> documents =
                vendorDocumentRepository.findByVendor(vendor);

        List<DocumentType> documentTypes =
                documentTypeRepository.findAll();

        double score = ComplianceScoringEngine.calculateScore(
                documentTypes, documents);

        String rating = ComplianceScoringEngine.deriveRating(score);

        return new ComplianceScoreResult(score, rating);
    }
}
