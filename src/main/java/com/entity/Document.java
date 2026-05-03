package com.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String content;

    @Enumerated(EnumType.STRING)
    private com.entity.DocumentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User submittedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private User approver;

    private LocalDateTime createdAt; //time never change

    private LocalDateTime updatedAt;
}