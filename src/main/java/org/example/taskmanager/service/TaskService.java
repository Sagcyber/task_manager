package org.example.taskmanager.service;

import org.example.taskmanager.model.Task;
import org.example.taskmanager.model.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    
    private final List<Task> tasks = new ArrayList<>();
    
    public List<Task> getAllTasks() {
        return List.copyOf(tasks);
    }
    
    public Task addTask(String taskName, TaskStatus status, LocalDate deadline) {
        Task task = new Task(taskName, status, deadline);
        tasks.add(task);
        return task;
    }
    
    public Task getTaskById(long id) {
        return tasks.stream()
                       .filter(task -> task.getId() == id)
                       .findFirst()
                       .orElse(null);
    }
    
    public boolean deleteTask(long id) {
        return tasks.removeIf(task -> task.getId() == id);
    }
    
}
