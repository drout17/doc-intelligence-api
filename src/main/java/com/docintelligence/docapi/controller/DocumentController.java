package com.docintelligence.docapi.controller;

import com.docintelligence.docapi.model.Document;
import com.docintelligence.docapi.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a document", description = "Upload a PDF or text file")
    public ResponseEntity<Document> uploadDocument(
            @RequestParam("file") MultipartFile file) {
        try {
            Document document = documentService.uploadDocument(file);
            return ResponseEntity.ok(document);
        } catch (IOException e) {
            log.error("Error uploading document: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }
}