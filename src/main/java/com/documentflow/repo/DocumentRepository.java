package com.documentflow.repo;

import com.documentflow.entity.Document;
import com.documentflow.entity.enums.DocumentStatus;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.HibernateHints;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByStatus(DocumentStatus status);

    List<Document> findByApproverUsername(String username);

//    Example<? extends Document> id(Long id);

    @Query("SELECT d FROM Document d JOIN FETCH d.approver JOIN FETCH d.submittedBy WHERE d.id = :id")
    Optional<Document> findFullById(@Param("id") Long id);

}