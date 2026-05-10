package com.documentflow.integration;

import com.documentflow.entity.enums.DocumentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentEvent {

    private Long documentId;
    private String action;
    private String performedBy;
}
