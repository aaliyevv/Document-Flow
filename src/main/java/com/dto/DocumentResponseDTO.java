package com.dto;

import com.entity.DocumentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentResponseDTO {
    private Long id;
    private String title;
    private DocumentStatus status;
}