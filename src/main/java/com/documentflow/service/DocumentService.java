package com.documentflow.service;

import com.documentflow.dto.*;
import com.documentflow.dto.DocumentRequestDTO;
import com.documentflow.dto.DocumentResponseDTO;
import com.documentflow.entity.Document;
import com.documentflow.entity.User;
import com.documentflow.entity.enums.DocumentStatus;
import com.documentflow.exception.NotFoundException;
import com.documentflow.exception.UnauthorizedActionException;
import com.documentflow.integration.DocumentEvent;
import com.documentflow.repo.DocumentRepository;
import com.documentflow.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;
//    private final MessageChannel documentChannel;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public DocumentResponseDTO submitDocument(DocumentRequestDTO dto, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        User approver = userRepository.findById(dto.getApproverId())
                .orElseThrow(() -> new NotFoundException("Approver not found"));

        Document document = Document.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .status(DocumentStatus.PENDING_APPROVAL)
                .submittedBy(user)
                .approver(approver)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        documentRepository.save(document);

        rabbitTemplate.convertAndSend("document.queue",
                        new DocumentEvent(
                                document.getId(),
                                "SUBMITTED",
                                approver.getEmail(),
                                user.getEmail(),
                                document.getTitle()
                        )
                );

        auditService.log(
                "DOCUMENT_SUBMITTED",
                "Document submitted for approval",
                document.getId(),
                username
        );

        return mapToResponse(document);
    }

    @Transactional
    public DocumentResponseDTO approveDocument(
            Long documentId,
            String approverUsername,
            ApprovalRequest request
    ) {

        Document document = getDocument(documentId);

        if (!document.getApprover().getUsername().equals(approverUsername)) {
            throw new UnauthorizedActionException("You are not assigned approver");
        }

        rabbitTemplate.convertAndSend("document.queue",
                        new DocumentEvent(
                                document.getId(),
                                "APPROVED",
                                document.getApprover().getEmail(),
                                document.getSubmittedBy().getEmail(),
                                document.getTitle()
                        )
                );

        auditService.log(
                "DOCUMENT_APPROVED",
                request.getComment(),
                documentId,
                approverUsername
        );

        return mapToResponse(document);
    }

    @Transactional
    public DocumentResponseDTO rejectDocument(
            Long documentId,
            String approverUsername,
            ApprovalRequest request
    ) {

        Document document = getDocument(documentId);

        if (!document.getApprover().getUsername().equals(approverUsername)) {
            throw new UnauthorizedActionException("You are not assigned approver");
        }

        rabbitTemplate.convertAndSend("document.queue",
                        new DocumentEvent(
                                document.getId(),
                                "REJECTED",
                                document.getApprover().getEmail(),
                                document.getSubmittedBy().getEmail(),
                                document.getTitle()
                        )
                );

        auditService.log(
                "DOCUMENT_REJECTED",
                request.getComment(),
                documentId,
                approverUsername
        );

        return mapToResponse(document);
    }

    private Document getDocument(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Document not found"));
    }

    private DocumentResponseDTO mapToResponse(Document document) {

        return DocumentResponseDTO.builder()
                .id(document.getId())
                .title(document.getTitle())
                .status(document.getStatus())
                .build();
    }
}