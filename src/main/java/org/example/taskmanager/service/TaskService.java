package org.example.taskmanager.service;

import org.example.taskmanager.dto.TaskRequestDto;
import org.example.taskmanager.dto.TaskResponseDto;
import org.example.taskmanager.model.TaskStatus;

import java.util.List;

public interface TaskService {
    
    TaskResponseDto addTask(TaskRequestDto dto);
    
    List<TaskResponseDto> getAllTasks();
    
    TaskResponseDto getTaskById(Long id);
    
    List<TaskResponseDto> getTasksByCategory(String categoryName);
    
    List<TaskResponseDto> getTasksByStatus(TaskStatus status);
    
    TaskResponseDto updateTask(Long id, TaskRequestDto dto);
    
    void deleteTask(Long id);
}
