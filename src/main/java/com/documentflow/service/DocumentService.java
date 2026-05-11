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
}