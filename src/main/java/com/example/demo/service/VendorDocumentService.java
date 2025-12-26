package com.example.demo.service;

import com.example.demo.model.VendorDocument;

public interface VendorDocumentService {

    VendorDocument uploadDocument(Long vendorId, Long typeId, VendorDocument document);

    VendorDocument getDocument(Long id);
}
