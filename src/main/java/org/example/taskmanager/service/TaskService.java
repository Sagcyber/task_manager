package org.example.taskmanager.service;

import org.example.taskmanager.model.Task;
import org.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }
    
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    
    public List<Task> getTasksByCategory(DELETE category) {
        return taskRepository.findByCategory(category);
    }
    
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }
    
}
