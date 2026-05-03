package com.service;

import com.dto.*;
import com.entity.*;
import com.entity.DocumentStatus;
import com.exception.NotFoundException;
import com.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;

    public DocumentResponseDTO submitDocument(DocumentRequestDTO dto, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        User approver = userRepository.findById(dto.getApproverId())
                .orElseThrow(() -> new NotFoundException("Approver not found"));

        Document doc = Document.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .status(DocumentStatus.SUBMITTED)
                .submittedBy(user)
                .approver(approver)
                .createdAt(LocalDateTime.now())
                .build();

        documentRepository.save(doc);

        auditLogRepository.save(
                AuditLog.builder()
                        .action("DOCUMENT_SUBMITTED")
                        .details("Document ID: " + doc.getId())
                        .timestamp(LocalDateTime.now())
                        .build()
        );

        return DocumentResponseDTO.builder()
                .id(doc.getId())
                .title(doc.getTitle())
                .status(doc.getStatus())
                .build();
    }
}