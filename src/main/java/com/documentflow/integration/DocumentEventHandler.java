package com.documentflow.integration;

import com.documentflow.entity.Document;
import com.documentflow.entity.enums.DocumentStatus;
import com.documentflow.repo.DocumentRepository;
import com.documentflow.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

// listen to documentChannel, receive documentEvent and update status

@Component
@RequiredArgsConstructor
public class DocumentEventHandler {

    private final DocumentRepository documentRepository;
    private final EmailService emailService;

    @Transactional
    @ServiceActivator(inputChannel = "documentChannel")
    public void handleDocumentEvent(DocumentEvent event) {

        Document document = documentRepository.findById(event.getDocumentId())
                .orElseThrow(() -> new RuntimeException("Document not found"));

        switch (event.getAction()) {

            case "SUBMITTED":
                document.setStatus(DocumentStatus.PENDING_APPROVAL);

                emailService.sendEmail(
                        "Document Approval Request",
                        buildSubmissionMessage(event.getTitle())
                );
                break;

            case "APPROVED":
                document.setStatus(DocumentStatus.APPROVED);

                emailService.sendEmail(
                        "Document Approved",
                        "Your document has been approved."
                );
                break;

            case "REJECTED":
                document.setStatus(DocumentStatus.REJECTED);

                emailService.sendEmail(
                        "Document Rejected",
                        "Your document has been rejected."
                );
                break;
        }

        document.setUpdatedAt(LocalDateTime.now());
        documentRepository.save(document);
    }

    private String buildSubmissionMessage(String title) {
        return "A new document requires your approval.\n\nTitle: " + title;
    }
}