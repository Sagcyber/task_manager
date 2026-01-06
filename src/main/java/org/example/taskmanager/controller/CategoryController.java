package org.example.taskmanager.controller;

import org.example.taskmanager.dto.CategoryRequestDto;
import org.example.taskmanager.dto.CategoryResponseDto;
import org.example.taskmanager.model.Category;
import org.example.taskmanager.repository.CategoryRepository;
import org.example.taskmanager.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    private final CategoryService categoryService;
    
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @PostMapping
    public CategoryResponseDto create(@RequestBody CategoryRequestDto dto) {
        return categoryService.create(dto);
    }
    
    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return categoryService.findAll();
    }
}
