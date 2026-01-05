package org.example.taskmanager.service;

import org.example.taskmanager.dto.TaskRequestDto;
import org.example.taskmanager.dto.TaskResponseDto;
import org.example.taskmanager.exception.CategoryNotFoundException;
import org.example.taskmanager.exception.TaskNotFoundException;
import org.example.taskmanager.mapper.TaskMapper;
import org.example.taskmanager.model.Category;
import org.example.taskmanager.model.Task;
import org.example.taskmanager.repository.CategoryRepository;
import org.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    private final CategoryRepository categoryRepository;
    
    private final TaskMapper taskMapper = new TaskMapper();
    
    public TaskService(TaskRepository taskRepository,
                       CategoryRepository categoryRepository
    ) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }
    
    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll()
                       .stream()
                       .map(taskMapper::toDto)
                       .toList();
    }
    
    public TaskResponseDto addTask(TaskRequestDto dto) {
        Category category = categoryRepository.findAll().stream()
                                              .filter(c -> c.getName().equals(dto.getCategoryName()))
                                              .findFirst()
                                              .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryName()));
        
        Task task = taskMapper.toEntity(dto, category);
        Task savedTask = taskRepository.save(task);
        
        return taskMapper.toDto(savedTask);
    }
    
    public TaskResponseDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                                  .orElseThrow(() -> new TaskNotFoundException(id));
        
        return taskMapper.toDto(task);
    }
    
    public List<TaskResponseDto> getTasksByCategory(String name) {
        return taskRepository.findByCategory_Name(name)
                       .stream()
                       .map(taskMapper::toDto)
                       .toList();
    }
    
    public TaskResponseDto updateTask(Long id, TaskRequestDto dto) {
        Task task = taskRepository.findById(id)
                            .orElseThrow();
        
        Category category = categoryRepository.findAll().stream()
                                              .filter(c -> c.getName().equals(dto.getCategoryName()))
                                              .findFirst()
                                              .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryName()));
        
        task.setTaskName(dto.getTaskName());
        task.setStatus(dto.getStatus());
        task.setDeadline(dto.getDeadline());
        task.setCategory(category);
        
        return taskMapper.toDto(taskRepository.save(task));
    }
    
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }
    
}
