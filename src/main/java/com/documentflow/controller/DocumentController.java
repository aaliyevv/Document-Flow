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


}