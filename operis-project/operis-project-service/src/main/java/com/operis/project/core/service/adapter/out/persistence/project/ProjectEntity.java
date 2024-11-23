package com.operis.project.core.service.adapter.out.persistence.project;

import com.operis.project.core.application.project.model.Project;
import com.operis.project.core.application.project.model.ProjectMember;
import com.operis.project.core.application.project.model.ProjectOwner;
import com.operis.project.core.service.adapter.out.persistence.task.TaskEntity;
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

    private String description;

    @Column(nullable = false)
    private String ownerEmail;

    @ElementCollection
    @CollectionTable(
            name = "project_members_emails",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Column(name = "member_email")
    private List<String> membersEmails = new ArrayList<>();

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
                false
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
                new ProjectOwner(this.ownerEmail),
                this.name,
                this.description,
                this.createdAt,
                this.tasks.stream().map(TaskEntity::toProjectDomain).toList(),
                this.membersEmails.stream().map(ProjectMember::new).toList()
        );
    }
}
