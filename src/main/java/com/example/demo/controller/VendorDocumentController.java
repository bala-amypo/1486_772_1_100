package com.example.demo.controller;

import com.example.demo.model.VendorDocument;
import com.example.demo.service.VendorDocumentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-documents")
public class VendorDocumentController {

    private final VendorDocumentService vendorDocumentService;

    public VendorDocumentController(VendorDocumentService vendorDocumentService) {
        this.vendorDocumentService = vendorDocumentService;
    }

    @PostMapping
    public ResponseEntity<VendorDocument> uploadDocument(
            @RequestParam Long vendorId,
            @RequestParam Long typeId,
            @RequestBody VendorDocument document) {

        return ResponseEntity.ok(
                vendorDocumentService.uploadDocument(vendorId, typeId, document)
        );
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<VendorDocument>> getForVendor(
            @PathVariable Long vendorId) {

        return ResponseEntity.ok(
                vendorDocumentService.getDocumentsForVendor(vendorId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorDocument> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                vendorDocumentService.getDocument(id)
        );
    }
}
