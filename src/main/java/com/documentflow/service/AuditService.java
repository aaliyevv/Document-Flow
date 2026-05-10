package com.documentflow.service;

import com.documentflow.entity.AuditLog;
import com.documentflow.repo.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void log(
            String action,
            String details,
            Long documentId,
            String performedBy
    ){
        AuditLog log = AuditLog.builder()
                .action(action)
                .details(details)
                .documentId(documentId)
                .performedBy(performedBy)
                .build();

        auditLogRepository.save(log);
    }
}
