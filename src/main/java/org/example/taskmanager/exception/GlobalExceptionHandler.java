package org.example.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleTaskNotFound(TaskNotFoundException ex) {
        return ResponseEntity
                       .status(HttpStatus.NOT_FOUND)
                       .body(new ApiErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }
    
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleCategoryNotFound(CategoryNotFoundException ex) {
        return ResponseEntity
                       .status(HttpStatus.NOT_FOUND)
                       .body(new ApiErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
                       .status(HttpStatus.INTERNAL_SERVER_ERROR)
                       .body(new ApiErrorResponse(
                               "Unexpected error occurred",
                               HttpStatus.INTERNAL_SERVER_ERROR.value()
                       ));
    }
    
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleCategoryExists(CategoryAlreadyExistsException ex) {
        return ResponseEntity
                       .status(HttpStatus.CONFLICT)
                       .body(new ApiErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value()));
    }
}
