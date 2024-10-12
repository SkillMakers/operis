package com.operis.project.core.application.report.model;

import com.operis.project.core.application.project.model.ProjectOwner;
import com.operis.project.core.application.task.model.Task;

import java.util.List;

public record ProjectSummary(
        String projectName,
        String projectDescription,
        ProjectOwner owner,
        List<Task> tasks
) {

}
