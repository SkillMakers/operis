package com.operis.project.core.application.task.port.out.persistence;

import com.operis.project.core.application.task.model.Task;
import com.operis.project.core.application.task.model.TaskCriteria;

import java.util.List;

public interface TaskRepository {
    Task save(Task task);

    void deleteById(String eq);

    List<Task> getTasks(TaskCriteria criteria);
}
