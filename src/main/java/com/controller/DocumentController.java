package com.controller;

import com.dto.*;
import com.service.DocumentService;
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