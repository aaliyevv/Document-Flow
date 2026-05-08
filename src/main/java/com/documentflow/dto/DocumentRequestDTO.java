package com.documentflow.dto;

import lombok.Data;

@Data
public class DocumentRequestDTO {
    private String title;
    private String content;
    private Long approverId;
}