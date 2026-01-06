package org.example.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.taskmanager.dto.CategoryRequestDto;
import org.example.taskmanager.dto.CategoryResponseDto;
import org.example.taskmanager.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categories", description = "Category management API")
@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    private final CategoryService categoryService;
    
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @Operation(summary = "Create category")
    @PostMapping
    public CategoryResponseDto create(@RequestBody CategoryRequestDto dto) {
        return categoryService.create(dto);
    }
    
    @Operation(summary = "Get all categories")
    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return categoryService.findAll();
    }
}
