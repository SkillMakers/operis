package com.operis.project.core.service.adapter.out.persistence;

import com.operis.project.core.application.project.model.Project;
import com.operis.project.core.application.project.model.ProjectMember;
import com.operis.project.core.application.project.model.ProjectOwner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projects")
public class ProjectEntity {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String ownerId; //  e-mail de l'utilisateur propriétaire

    @ElementCollection
    private List<String> memberIds = new ArrayList<>(); // Liste d'e-mails des membres

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskEntity> tasks = new ArrayList<>();

    private LocalDateTime createdAt;

    private boolean archived = false;

    public static ProjectEntity from(Project project) {
        ProjectEntity projectEntity = new ProjectEntity(
                project.id(),
                project.name(),
                project.description(),
                project.owner().userEmail(),
                project.members().stream().map(ProjectMember::getUserEmail).toList(),
                null,
                project.createdAt(),
                project.archived()
        );
        projectEntity.setTasks(project.tasks().stream()
                .map(task -> TaskEntity.from(task, projectEntity))
                .toList()
        );

        return projectEntity;
    }

    public Project toDomain() {
        return new Project(
                this.id,
                new ProjectOwner(this.ownerId),
                this.name,
                this.description,
                this.createdAt,
                this.tasks.stream().map(TaskEntity::toDomain).toList(),
                this.memberIds.stream().map(ProjectMember::new).toList(),
                this.archived
        );
    }
}
