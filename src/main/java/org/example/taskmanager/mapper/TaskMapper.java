package org.example.taskmanager.mapper;

import org.example.taskmanager.dto.TaskRequestDto;
import org.example.taskmanager.dto.TaskResponseDto;
import org.example.taskmanager.model.Category;
import org.example.taskmanager.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    
    public Task toEntity(TaskRequestDto dto, Category category) {
        Task task = new Task();
        task.setTaskName(dto.getTaskName());
        task.setStatus(dto.getStatus());
        task.setDeadline(dto.getDeadline());
        task.setCategory(category);
        return task;
    }
    
    public TaskResponseDto toDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setTaskName(task.getTaskName());
        dto.setStatus(task.getStatus());
        dto.setDeadline(task.getDeadline());
        dto.setCategoryName(task.getCategory().getName());
        return dto;
    }
}
