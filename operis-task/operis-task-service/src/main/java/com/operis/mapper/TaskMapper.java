package com.operis.mapper;

import com.operis.dto.TaskDto;
import com.operis.dto.TaskHistoryDto;
import com.operis.model.Task;
import com.operis.model.TaskHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskDto taskToTaskDto(Task task);
    Task taskDtoToTask(TaskDto taskDto);

    TaskHistoryDto taskHistoryToTaskHistoryDto(TaskHistory taskHistory);
    TaskHistory taskHistoryDtoToTaskHistory(TaskHistoryDto taskHistoryDto);
}
