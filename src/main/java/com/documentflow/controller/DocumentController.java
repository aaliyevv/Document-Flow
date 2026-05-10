package com.documentflow.controller;

import com.documentflow.dto.*;
import com.documentflow.dto.DocumentRequestDTO;
import com.documentflow.dto.DocumentResponseDTO;
import com.documentflow.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public DocumentResponseDTO submitDocument(
            @RequestBody DocumentRequestDTO dto,
            Authentication authentication) {

        return documentService.submitDocument(dto, authentication.getName());
    }

    @PutMapping("/{id}/approve")
    public DocumentResponseDTO approveDocument(
            @PathVariable Long id,
            @RequestBody ApprovalRequest approvalRequest,
            Authentication authentication
    ){
        return documentService.approveDocument(
                id,authentication.getName(), approvalRequest);
    }

    @PutMapping("/{id}/reject")
    public DocumentResponseDTO rejectDocument(
            @PathVariable Long id,
            @RequestBody ApprovalRequest approvalRequest,
            Authentication authentication
    ){
        return documentService.rejectDocument(
                id, authentication.getName(), approvalRequest);
    }
}