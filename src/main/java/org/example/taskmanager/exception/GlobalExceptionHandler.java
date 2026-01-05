package org.example.taskmanager.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFound(TaskNotFoundException ex) {
        log.error("Task not found error: {}", ex.getMessage());
        return ResponseEntity
                       .status(HttpStatus.NOT_FOUND)
                       .body(ex.getMessage());
    }
    
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFound(CategoryNotFoundException ex) {
        log.error("Category not found error: {}", ex.getMessage());
        return ResponseEntity
                       .status(HttpStatus.NOT_FOUND)
                       .body(ex.getMessage());
    }
    
}
