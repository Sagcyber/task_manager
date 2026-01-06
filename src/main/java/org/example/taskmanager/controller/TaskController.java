package org.example.taskmanager.controller;

import org.example.taskmanager.dto.TaskRequestDto;
import org.example.taskmanager.dto.TaskResponseDto;
import org.example.taskmanager.model.TaskStatus;
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
    
    @PostMapping
    public TaskResponseDto create(@RequestBody TaskRequestDto dto) {
        return taskService.addTask(dto);
    }
    
    @GetMapping
    public List<TaskResponseDto> getAll() {
        return taskService.getAllTasks();
    }
    
    @GetMapping("/{id}")
    public TaskResponseDto getById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
    
    @GetMapping("/category/{name}")
    public List<TaskResponseDto> getByCategory(@PathVariable String name) {
        return taskService.getTasksByCategory(name);
    }
    
    @GetMapping("/status/{status}")
    public List<TaskResponseDto> getByStatus(@PathVariable TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }
    
    @PutMapping("/{id}")
    public TaskResponseDto update(@PathVariable Long id,
                                  @RequestBody TaskRequestDto dto) {
        return taskService.updateTask(id, dto);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
