package com.example.demo.repository;

import com.example.demo.model.VendorDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorDocumentRepository extends JpaRepository<VendorDocument, Long> {

    // REQUIRED by ComplianceScoreService + tests
    List<VendorDocument> findByVendorId(Long vendorId);
}
