package org.example.taskmanager.exception;

public class CategoryNotFoundException extends RuntimeException {
    
    public CategoryNotFoundException(String name) {
        super("Category not found: " + name);
    }
}
