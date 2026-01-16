package org.example.taskmanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.taskmanager.dto.TaskRequestDto;
import org.example.taskmanager.dto.TaskResponseDto;
import org.example.taskmanager.exception.CategoryNotFoundException;
import org.example.taskmanager.exception.TaskNotFoundException;
import org.example.taskmanager.kafka.TaskCreatedEvent;
import org.example.taskmanager.kafka.TaskEventProducer;
import org.example.taskmanager.mapper.TaskMapper;
import org.example.taskmanager.model.Category;
import org.example.taskmanager.model.Task;
import org.example.taskmanager.model.TaskStatus;
import org.example.taskmanager.repository.CategoryRepository;
import org.example.taskmanager.repository.TaskRepository;
import org.example.taskmanager.service.TaskService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TaskMapper taskMapper;
    private final TaskEventProducer taskEventProducer;
    
    public TaskServiceImpl(TaskRepository taskRepository,
                           CategoryRepository categoryRepository,
                           TaskMapper taskMapper,
                           TaskEventProducer taskEventProducer) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.taskMapper = taskMapper;
        this.taskEventProducer = taskEventProducer;
    }
    
    @Override
    @Cacheable("tasks")
    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll()
                             .stream()
                             .map(taskMapper::toDto)
                             .toList();
    }
    
    @Override
    @Cacheable(value = "taskById", key = "#id")
    public TaskResponseDto getTaskById(Long id) {
        log.info("Fetching task from DB, id={}", id);
        
        Task task = taskRepository.findById(id)
                                  .orElseThrow(() -> new TaskNotFoundException(id));
        
        return taskMapper.toDto(task);
    }
    
    @Override
    public List<TaskResponseDto> getTasksByCategory(String categoryName) {
        return taskRepository.findByCategory_Name(categoryName)
                             .stream()
                             .map(taskMapper::toDto)
                             .toList();
    }
    
    @Override
    @Cacheable(value = "tasksByStatus", key = "#status")
    public List<TaskResponseDto> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status)
                             .stream()
                             .map(taskMapper::toDto)
                             .toList();
    }
    
    @Override
    @Transactional
    @CacheEvict(value = {"tasks", "tasksByStatus", "taskById"}, allEntries = true)
    public TaskResponseDto addTask(TaskRequestDto dto) {
        log.info("Creating task: name={}, category={}",
                 dto.getTaskName(),
                 dto.getCategoryName());
        
        Category category = categoryRepository.findByName(dto.getCategoryName())
                                              .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryName()));
        
        Task task = taskMapper.toEntity(dto, category);
        Task savedTask = taskRepository.save(task);
        
        taskEventProducer.sendTaskCreated(
                new TaskCreatedEvent(
                        savedTask.getId(),
                        savedTask.getTaskName(),
                        savedTask.getCategory().getName()
                )
        );
        
        log.info("Task created successfully with id={}", savedTask.getId());
        
        return taskMapper.toDto(savedTask);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = {"tasks", "tasksByStatus", "taskById"}, allEntries = true)
    public TaskResponseDto updateTask(Long id, TaskRequestDto dto) {
        Task task = taskRepository.findById(id)
                                  .orElseThrow(() -> new TaskNotFoundException(id));
        
        Category category = categoryRepository.findByName(dto.getCategoryName())
                                              .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryName()));
        
        task.setTaskName(dto.getTaskName());
        task.setStatus(dto.getStatus());
        task.setDeadline(dto.getDeadline());
        task.setCategory(category);
        
        return taskMapper.toDto(taskRepository.save(task));
    }
    
    @Override
    @Transactional
    @CacheEvict(value = {"tasks", "tasksByStatus", "taskById"}, allEntries = true)
    public void deleteTask(Long id) {
        log.info("Deleting task with id={}", id);
        
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        
        taskRepository.deleteById(id);
    }
}
