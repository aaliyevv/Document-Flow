package com.documentflow.integration;

import com.documentflow.entity.Document;
import com.documentflow.entity.enums.DocumentStatus;
import com.documentflow.repo.DocumentRepository;
import com.documentflow.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

// listen to documentChannel, receive documentEvent and update status

@Component
@RequiredArgsConstructor
public class DocumentEventHandler {
}
