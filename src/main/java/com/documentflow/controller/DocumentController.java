package com.documentflow.controller;

import com.documentflow.dto.*;
import com.documentflow.dto.DocumentRequestDTO;
import com.documentflow.dto.DocumentResponseDTO;
import com.documentflow.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public DocumentResponseDTO submit(
            @RequestBody DocumentRequestDTO dto,
            @RequestHeader("X-USER") String username
    ) {
        return documentService.submitDocument(dto, username);
    }
}