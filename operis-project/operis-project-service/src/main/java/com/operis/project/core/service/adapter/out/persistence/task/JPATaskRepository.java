package com.operis.project.core.service.adapter.out.persistence.task;

import com.operis.project.core.application.task.model.Task;
import com.operis.project.core.application.task.model.TaskCriteria;
import com.operis.project.core.application.task.port.out.persistence.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JPATaskRepository implements TaskRepository {
    private final JPATaskSpringDataRepository jpaTaskSpringDataRepository;

    @Override
    public Task save(Task task) {
        TaskEntity taskEntity = TaskEntity.from(task);
        jpaTaskSpringDataRepository.save(taskEntity);
        return task;
    }

    @Override
    public void deleteById(String taskId) {
        jpaTaskSpringDataRepository.deleteById(taskId);
    }

    @Override
    public List<Task> getTasks(TaskCriteria criteria) {
        return jpaTaskSpringDataRepository.findAll(
                        criteria.projectId(),
                        TaskStatusEntity.from(criteria.status()),
                        criteria.from(),
                        criteria.to())
                .stream().map(TaskEntity::toTaskDomain)
                .toList();
    }
}
