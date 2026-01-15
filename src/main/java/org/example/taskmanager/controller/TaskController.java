package org.example.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.taskmanager.dto.TaskRequestDto;
import org.example.taskmanager.dto.TaskResponseDto;
import org.example.taskmanager.model.TaskStatus;
import org.example.taskmanager.service.TaskService;
import org.example.taskmanager.service.impl.TaskServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tasks", description = "Task management API")
@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    private final TaskService taskService;
    
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @Operation(summary = "Create new task")
    @PostMapping
    public TaskResponseDto create(@RequestBody TaskRequestDto dto) {
        return taskService.addTask(dto);
    }
    
    @Operation(summary = "Get all tasks")
    @GetMapping
    public List<TaskResponseDto> getAll() {
        return taskService.getAllTasks();
    }
    
    @Operation(summary = "Get tasks by id")
    @GetMapping("/{id}")
    public TaskResponseDto getById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
    
    @Operation(summary = "Get tasks by category")
    @GetMapping("/category/{name}")
    public List<TaskResponseDto> getByCategory(@PathVariable String name) {
        return taskService.getTasksByCategory(name);
    }
    
    @Operation(summary = "Get tasks by status")
    @GetMapping("/status/{status}")
    public List<TaskResponseDto> getByStatus(@PathVariable TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }
    
    @Operation(summary = "Update task")
    @PutMapping("/{id}")
    public TaskResponseDto update(@PathVariable Long id,
                                  @RequestBody TaskRequestDto dto) {
        return taskService.updateTask(id, dto);
    }
    
    @Operation(summary = "Delete task by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
