package com.example.demo.controller;

import com.example.demo.model.VendorDocument;
import com.example.demo.service.VendorDocumentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-documents")
public class VendorDocumentController {

    private final VendorDocumentService vendorDocumentService;

    public VendorDocumentController(VendorDocumentService vendorDocumentService) {
        this.vendorDocumentService = vendorDocumentService;
    }

    @PostMapping("/{vendorId}/{typeId}")
    public VendorDocument uploadDocument(@PathVariable Long vendorId,
                                         @PathVariable Long typeId,
                                         @RequestBody VendorDocument document) {
        return vendorDocumentService.uploadDocument(vendorId, typeId, document);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<VendorDocument> getDocumentsByVendor(@PathVariable Long vendorId) {
        return vendorDocumentService.getDocumentsByVendor(vendorId);
    }

    @GetMapping("/{id}")
    public VendorDocument getDocument(@PathVariable Long id) {
        return vendorDocumentService.getDocument(id);
    }
}
