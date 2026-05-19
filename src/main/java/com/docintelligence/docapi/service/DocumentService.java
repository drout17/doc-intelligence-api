package com.docintelligence.docapi.service;

import com.docintelligence.docapi.model.Document;
import com.docintelligence.docapi.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.Loader;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    public Document uploadDocument(MultipartFile file) throws IOException {
        log.info("Processing document: {}", file.getOriginalFilename());

        // Extract text based on file type
        String content = extractText(file);

        // Save to database
        Document document = new Document();
        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        document.setContent(content);

        Document saved = documentRepository.save(document);
        log.info("Document saved with id: {}", saved.getId());

        return saved;
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    private String extractText(MultipartFile file) throws IOException {
        String contentType = file.getContentType();

        if ("application/pdf".equals(contentType)) {
            // Extract text from PDF using PDFBox
            try (PDDocument pdDocument = Loader.loadPDF(file.getBytes())) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(pdDocument);
            }
        } else if (contentType != null && contentType.startsWith("text/")) {
            // Plain text files
            return new String(file.getBytes());
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + contentType);
        }
    }
}