package org.example.taskmanager.service;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.extern.slf4j.Slf4j;
import org.example.taskmanager.dto.TaskRequestDto;
import org.example.taskmanager.dto.TaskResponseDto;
import org.example.taskmanager.exception.CategoryNotFoundException;
import org.example.taskmanager.exception.TaskNotFoundException;
import org.example.taskmanager.mapper.TaskMapper;
import org.example.taskmanager.model.Category;
import org.example.taskmanager.model.Task;
import org.example.taskmanager.model.TaskStatus;
import org.example.taskmanager.model.User;
import org.example.taskmanager.repository.CategoryRepository;
import org.example.taskmanager.repository.TaskRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskService {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    private final TaskRepository taskRepository;
    
    private final CategoryRepository categoryRepository;
    
    private final TaskMapper taskMapper = new TaskMapper();
    
    public TaskService(TaskRepository taskRepository,
                       CategoryRepository categoryRepository
    ) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }
    
    @Cacheable("tasks")
    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll()
                       .stream()
                       .map(taskMapper::toDto)
                       .toList();
    }
    
    public TaskResponseDto addTask(TaskRequestDto dto) {
        log.info("Creating task: name={}, category={}",
                 dto.getTaskName(),
                 dto.getCategoryName());
        
        Category category = categoryRepository.findAll().stream()
                                              .filter(c -> c.getName().equals(dto.getCategoryName()))
                                              .findFirst()
                                              .orElseThrow(() ->
                                                                   new CategoryNotFoundException(dto.getCategoryName()));
        
        Task task = taskMapper.toEntity(dto, category);
        Task savedTask = taskRepository.save(task);
        
        log.info("Task created successfully with id={}",
                 savedTask.getId());
        
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
    
    @Cacheable(value = "tasksByStatus", key = "#status")
    public List<TaskResponseDto> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status)
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
                                              .orElseThrow(() ->
                                                                   new CategoryNotFoundException(dto.getCategoryName()));
        
        task.setTaskName(dto.getTaskName());
        task.setStatus(dto.getStatus());
        task.setDeadline(dto.getDeadline());
        task.setCategory(category);
        
        return taskMapper.toDto(taskRepository.save(task));
    }
    
    public void deleteTask(long id) {
        log.info("Deleting task with id={}", id);
        taskRepository.deleteById(id);
    }
    
}
