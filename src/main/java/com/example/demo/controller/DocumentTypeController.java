package com.example.demo.controller;

import com.example.demo.model.DocumentType;
import com.example.demo.service.DocumentTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/document-types")
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    public DocumentTypeController(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @PostMapping
    public ResponseEntity<DocumentType> createDocumentType(@RequestBody DocumentType type) {
        return ResponseEntity.ok(documentTypeService.createDocumentType(type));
    }

    @GetMapping
    public ResponseEntity<List<DocumentType>> getAllDocumentTypes() {
        return ResponseEntity.ok(documentTypeService.getAllDocumentTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentType> getDocumentType(@PathVariable Long id) {
        return ResponseEntity.ok(documentTypeService.getDocumentType(id));
    }
}
