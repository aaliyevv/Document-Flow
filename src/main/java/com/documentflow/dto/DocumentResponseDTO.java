package com.documentflow.dto;

import com.documentflow.entity.enums.DocumentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentResponseDTO {
    private Long id;
    private String title;
    private DocumentStatus status;
}