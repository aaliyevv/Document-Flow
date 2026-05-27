package com.documentflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ApprovalRequest {

    @Schema(example = "Looks good, approved")
    private String comment;
}