package org.example.taskmanager.controller;

import org.example.taskmanager.dto.TaskRequestDto;
import org.example.taskmanager.dto.TaskResponseDto;
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
    public List<TaskResponseDto> getAllTasks() {
        return taskService.getAllTasks();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }
    
    @PostMapping
    public TaskResponseDto addTask(@RequestBody
                                   TaskRequestDto dto) {
        return taskService.addTask(dto);
    }
    
    
    @GetMapping("/category/{name}")
    public List<TaskResponseDto> getTasksByCategory(@PathVariable  String name) {
        return taskService.getTasksByCategory(name);
    }
    
    @PutMapping("/{id}")
    public TaskResponseDto updateTask(@PathVariable Long id, @RequestBody TaskRequestDto dto) {
        return taskService.updateTask(id, dto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    
}
