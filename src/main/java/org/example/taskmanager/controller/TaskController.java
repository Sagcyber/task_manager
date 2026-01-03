package org.example.taskmanager.controller;

import org.example.taskmanager.model.Task;
import org.example.taskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    private final TaskService taskService;
    
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                       .map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }
    
    @GetMapping("/category/{name}")
    public List<Task> getTasksByCategory(@PathVariable  String name) {
        return taskService.getTasksByCategory(name);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    
}
