package com.example.demo.service;

import com.example.demo.model.ComplianceScore;
import com.example.demo.model.DocumentType;
import com.example.demo.model.Vendor;
import com.example.demo.model.VendorDocument;

import java.util.List;

public interface ComplianceScoreService {

    ComplianceScore calculate(
            Vendor vendor,
            List<DocumentType> requiredTypes,
            List<VendorDocument> uploadedDocuments);
}
