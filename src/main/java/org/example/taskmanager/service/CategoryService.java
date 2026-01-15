package org.example.taskmanager.service;

import org.example.taskmanager.dto.CategoryRequestDto;
import org.example.taskmanager.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    
    CategoryResponseDto create(CategoryRequestDto dto);
    
    List<CategoryResponseDto> findAll();
    
    CategoryResponseDto getById(Long id);
    
    CategoryResponseDto update(Long id, CategoryRequestDto dto);
    
    void delete(Long id);
}
