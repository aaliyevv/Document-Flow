package com.documentflow.integration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentEvent {

    private Long documentId;
    private String action; // SUBMITTED, APPROVED, REJECTED

    private String approverEmail;
    private String submitterEmail;

    private String title;
}