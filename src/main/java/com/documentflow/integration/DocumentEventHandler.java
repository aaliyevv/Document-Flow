package com.documentflow.integration;

import com.documentflow.entity.Document;
import com.documentflow.entity.enums.DocumentStatus;
import com.documentflow.repo.DocumentRepository;
import com.documentflow.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

// listen to documentChannel, receive documentEvent and update status

@Component
@RequiredArgsConstructor
public class DocumentEventHandler {

    private final DocumentRepository documentRepository;
    private final EmailService emailService;

    @ServiceActivator(inputChannel = "documentChannel")
    public void handleDocumentEvent(DocumentEvent documentEvent) {

        try {

            Document document = documentRepository.findById(documentEvent.getDocumentId())
                    .orElseThrow(() -> new RuntimeException("Document not found"));


            switch (documentEvent.getAction()) {

                case "SUBMITTED":
                    document.setStatus(DocumentStatus.PENDING_APPROVAL);

                    emailService.sendEmail(
                            document.getApprover().getEmail(),
                            "Document Approval Request",
                            buildSubmissionMessage(document)
                    );
                    break;

                case "APPROVED":
                    document.setStatus(DocumentStatus.APPROVED);

                    emailService.sendEmail(
                            document.getSubmittedBy().getEmail(),
                            "Document Approved",
                            "Your document has been approved."
                    );
                    break;
}
