package com.documentflow.repo;

import com.documentflow.entity.Document;
import com.documentflow.entity.enums.DocumentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByStatus(DocumentStatus status);

    List<Document> findByApproverUsername(String username);
}