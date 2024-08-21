package com.operis.project.core.application.task.port.out.persistence;

import com.operis.project.core.application.task.model.Task;

public interface TaskRepository {
    Task save(Task task);

    void deleteById(String eq);
}
