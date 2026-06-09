package com.documentflow.integration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor // for JSON parsing
public class DocumentEvent {
    // SimpleMessageConverter only knows: string, byte[], Serializable

    private Long documentId;
    private String action; // SUBMITTED, APPROVED, REJECTED

    private String approverEmail;
    private String submitterEmail;

    private String title;
}