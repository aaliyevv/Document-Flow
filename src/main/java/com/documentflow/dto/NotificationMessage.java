package com.documentflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationMessage {

    private String message;
    private String status;
}
