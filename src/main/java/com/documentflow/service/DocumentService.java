package com.documentflow.service;

import com.documentflow.dto.*;
import com.documentflow.entity.*;
import com.documentflow.dto.DocumentRequestDTO;
import com.documentflow.dto.DocumentResponseDTO;
import com.documentflow.entity.AuditLog;
import com.documentflow.entity.Document;
import com.documentflow.entity.User;
import com.documentflow.entity.enums.DocumentStatus;
import com.documentflow.exception.NotFoundException;
import com.documentflow.integration.DocumentEvent;
import com.documentflow.repo.*;
import com.documentflow.repo.AuditLogRepository;
import com.documentflow.repo.DocumentRepository;
import com.documentflow.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;
    private final MessageChannel documentChannel;

    public DocumentResponseDTO submitDocument(DocumentRequestDTO dto, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        User approver = userRepository.findById(dto.getApproverId())
                .orElseThrow(() -> new NotFoundException("Approver not found"));

        Document document = Document.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .status(DocumentStatus.SUBMITTED)
                .submittedBy(user)
                .approver(approver)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        documentRepository.save(document);

        documentChannel.send(
                MessageBuilder.withPayload(
                        new DocumentEvent(document.getId(), "SUBMITTED", username)
                ).build()
        );

        auditService.log(
                "DOCUMENT_SUBMITTED",
                "Document submitted for approval",
                document.getId(),
                username
        );

        return mapToResponse(document);
    }

    // only the owner of action can do the same action

    public DocumentResponseDTO approveDocument(
            Long documentId,
            String approverUsername,
            ApprovalRequest  approvalRequest
    ) {

        Document document = getDocument(documentId);

        if (!document.getApprover().getUsername().equals(approverUsername)) {
            throw new RuntimeException("This is not approver");
        }

//        document.setStatus(DocumentStatus.APPROVED);
//        document.setUpdatedAt(LocalDateTime.now());
//        documentRepository.save(document);

        documentChannel.send(
                MessageBuilder.withPayload(
                        new DocumentEvent(documentId, "APPROVED", approverUsername))
                        .build()
                );


        auditService.log(
                "DOCUMENT_APPROVED",
                approvalRequest.getComment(),
                documentId,
                approverUsername
        );

        return mapToResponse(document);
    }

    public DocumentResponseDTO rejectDocument(
            Long documentId,
            String approverUsername,
            ApprovalRequest  approvalRequest
    ) {

        Document document = getDocument(documentId);

        if (!document.getApprover().getUsername().equals(approverUsername)) {
            throw new RuntimeException("This is not approver");
        }

//        document.setStatus(DocumentStatus.REJECTED);
//        document.setUpdatedAt(LocalDateTime.now());
//        documentRepository.save(document);

        documentChannel.send(
                MessageBuilder.withPayload(
                        new DocumentEvent(documentId, "REJECTED", approverUsername)
                ).build()
        );


        auditService.log(
                "DOCUMENT_REJECTED",
                approvalRequest.getComment(),
                documentId,
                approverUsername
        );

        return mapToResponse(document);
    }

}