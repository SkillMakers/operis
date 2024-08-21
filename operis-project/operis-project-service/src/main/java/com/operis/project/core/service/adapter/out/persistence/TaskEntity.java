package com.operis.project.core.service.adapter.out.persistence;

import com.operis.project.core.application.TaskHistory;
import com.operis.project.core.application.project.model.ProjectMember;
import com.operis.project.core.application.project.model.ProjectTask;
import com.operis.project.core.application.task.model.TaskOwner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class TaskEntity {

    @Id
    private String id;

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
    private TaskStatusEntity status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskHistory> history = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project; // Référence au projet auquel cette tâche est associée

    private LocalDateTime createdAt;

    public static TaskEntity from(ProjectTask projectTask, ProjectEntity projectEntity) {
        return new TaskEntity(
                projectTask.id(),
                projectTask.title(),
                projectTask.description(),
                projectTask.owner().userEmail(),
                projectTask.assignedTo().getUserEmail(),
                TaskStatusEntity.from(projectTask.status()),
                List.of(),
                projectEntity,
                projectTask.createdAt()
        );
    }

    public ProjectTask toDomain() {
        return new ProjectTask(
                this.id,
                this.title,
                this.description,
                new TaskOwner(this.ownerId),
                new ProjectMember(this.assigneeId),
                this.status.toDomain(),
                this.createdAt
        );
    }
}
