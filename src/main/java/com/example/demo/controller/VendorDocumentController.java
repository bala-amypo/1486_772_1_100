package com.example.demo.controller;

import com.example.demo.model.VendorDocument;
import com.example.demo.service.VendorDocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/documents")
public class VendorDocumentController {

    private final VendorDocumentService service;

    public VendorDocumentController(VendorDocumentService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<VendorDocument> upload(
            @RequestParam Long vendorId,
            @RequestParam Long typeId,
            @RequestBody VendorDocument document) {

        return ResponseEntity.ok(
                service.uploadDocument(vendorId, typeId, document)
        );
    }
}
