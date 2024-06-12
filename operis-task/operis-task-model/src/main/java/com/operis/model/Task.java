package com.operis.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String ownerId; // ID ou e-mail de l'utilisateur propriétaire

    @Column
    private String assigneeId; // ID ou e-mail de l'utilisateur assigné (peut être null)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskHistory> history = new ArrayList<>();

    @Column(nullable = false)
    private Long projectId; // ID du projet auquel la tâche est associée

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
