package com.operis.project.core.application.task.port.in;

import com.operis.project.core.application.task.model.CreateTaskCommand;
import com.operis.project.core.application.task.model.Task;

public interface TaskUseCases {
    Task createTask(CreateTaskCommand command);
}
