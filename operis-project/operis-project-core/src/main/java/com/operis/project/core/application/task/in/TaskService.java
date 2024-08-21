package com.operis.project.core.application.task.in;

import com.operis.project.core.application.task.model.CreateTaskCommand;
import com.operis.project.core.application.task.model.Task;
import com.operis.project.core.application.task.port.in.TaskUseCases;
import com.operis.project.core.application.task.port.out.persistence.TaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskService implements TaskUseCases {

    private final TaskRepository taskRepository;

    @Override
    public Task createTask(CreateTaskCommand command) {
        return taskRepository.save(
                new Task(command.id(), command.title(), command.description(), command.owner(), command.assignedTo()));
    }
}
