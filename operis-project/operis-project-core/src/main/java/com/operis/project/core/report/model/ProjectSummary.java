package com.operis.project.core.report.model;

import com.operis.project.core.project.model.ProjectOwner;
import com.operis.project.core.task.model.Task;

import java.util.List;

public record ProjectSummary(
        String projectName,
        String projectDescription,
        ProjectOwner owner,
        List<Task> tasks
) {

}
