package org.example.taskmanager.controller;

import org.example.taskmanager.model.Task;
import org.example.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    
    private final TaskService taskService;
    
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
    
    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable long id) {
        return taskService.getTaskById(id);
    }
    
    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task) {
        return taskService.addTask(task.getTaskName(), task.getStatus(), task.getDeadline());
    }
}
