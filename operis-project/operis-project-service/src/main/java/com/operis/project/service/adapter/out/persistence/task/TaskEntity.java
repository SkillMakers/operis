package com.operis.project.service.adapter.out.persistence.task;

import com.operis.project.core.project.model.ProjectMember;
import com.operis.project.core.project.model.ProjectTask;
import com.operis.project.core.task.model.Task;
import com.operis.project.core.task.model.TaskOwner;
import com.operis.project.service.adapter.out.persistence.project.ProjectEntity;
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
    private String ownerEmail;

    @Column
    private String assigneeToEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatusEntity status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskHistoryEntity> history = new ArrayList<>();

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

    public static TaskEntity from(Task task) {
        return new TaskEntity(
                task.id(),
                task.title(),
                task.description(),
                task.owner().userEmail(),
                task.assignedTo(),
                TaskStatusEntity.from(task.status()),
                List.of(),
                ProjectEntity.from(task.project()),
                task.createdAt()
        );
    }

    public ProjectTask toProjectDomain() {
        return new ProjectTask(
                this.id,
                this.title,
                this.description,
                new TaskOwner(this.ownerEmail),
                new ProjectMember(this.assigneeToEmail),
                this.status.toDomain(),
                this.createdAt
        );
    }

    public Task toTaskDomain() {
        return new Task(
                this.id,
                this.title,
                this.description,
                this.project.toDomain(),
                new TaskOwner(this.ownerEmail),
                this.assigneeToEmail,
                this.status.toDomain(),
                this.createdAt
        );
    }
}
