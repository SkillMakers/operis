package com.operis.mapper;

import com.operis.dto.TaskDto;
import com.operis.dto.TaskHistoryDto;
import com.operis.project.core.application.Task;
import com.operis.project.core.application.TaskHistory;
import org.mapstruct.Mapper;

@Mapper
public interface TaskMapper {

    TaskDto taskToTaskDto(Task task);

    Task taskDtoToTask(TaskDto taskDto);

    TaskHistoryDto taskHistoryToTaskHistoryDto(TaskHistory taskHistory);

    TaskHistory taskHistoryDtoToTaskHistory(TaskHistoryDto taskHistoryDto);
}
