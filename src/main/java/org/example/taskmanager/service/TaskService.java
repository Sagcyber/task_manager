package org.example.taskmanager.service;

import org.example.taskmanager.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    
    private final List<Task> tasks = new ArrayList<>();
    
    public List<Task> getAllTasks() {
        return List.copyOf(tasks);
    }
    
    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        tasks.add(task);
    }
}
