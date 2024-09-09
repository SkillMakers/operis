package com.operis.project.core.application.project.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public record Project(
        String id,
        ProjectOwner owner,
        String name,
        String description,
        LocalDateTime createdAt,
        List<ProjectTask> tasks,
        List<ProjectMember> members,
        boolean archived) {

    public Project(String id, ProjectOwner owner, String name, String description, List<ProjectTask> tasks, List<ProjectMember> members, boolean archived) {
        this(
                Objects.requireNonNull(id, "id must not be null"),
                Objects.requireNonNull(owner, "owner must not be null"),
                Objects.requireNonNull(name, "name must not be null"),
                description,
                LocalDateTime.now(),
                tasks == null ? new ArrayList<>() : tasks,
                members == null ? Arrays.asList(new ProjectMember(owner.userEmail())) : members,
                archived);
    }

    public Project(String id, ProjectOwner owner, String name, String description) {
        this(id, owner, name, description, null, null, false);
    }

    public Project(Project project, boolean archived) {
        this(project.id(), project.owner(), project.name(), project.description(), project.createdAt(),
                project.tasks(), project.members(), archived);

    }

    public Project(Project project, List<ProjectMember> members) {
        this(
                project.id(),
                project.owner(),
                project.name(),
                project.description(),
                project.createdAt(),
                project.tasks(),
                Stream.concat(Objects.requireNonNull(members, "members must not be null").stream(),
                                Stream.of(new ProjectMember(project.owner.userEmail())))
                        .distinct()
                        .toList(),
                false);
    }

    public Project copyAppendingTask(ProjectTask task) {
        return new Project(
                this.id(),
                this.owner(),
                this.name(),
                this.description(),
                this.createdAt(),
                Stream.concat(
                        Objects.requireNonNull(this.tasks, "tasks must not be null")
                                .stream(), Stream.of(task)).toList(),
                this.members,
                false);
    }

    public boolean isNotAMember(ProjectMember member) {
        return !isMember(member);
    }

    public boolean isMember(ProjectMember member) {
        return members.contains(member);
    }

    public void addTask(ProjectTask task) {
        if (!members.contains(task.assignedTo())) {
            throw new IllegalArgumentException("Task cannot be assigned to a non-member");
        }

        tasks.add(task);
    }
}
