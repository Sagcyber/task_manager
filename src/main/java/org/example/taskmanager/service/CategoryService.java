package org.example.taskmanager.service;

import org.example.taskmanager.dto.CategoryRequestDto;
import org.example.taskmanager.dto.CategoryResponseDto;
import org.example.taskmanager.mapper.CategoryMapper;
import org.example.taskmanager.model.Category;
import org.example.taskmanager.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    public CategoryService(CategoryRepository categoryRepository,
                           CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }
    
    public CategoryResponseDto create(CategoryRequestDto dto) {
        Category category = categoryMapper.toEntity(dto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }
    
    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll()
                       .stream()
                       .map(categoryMapper::toDto)
                       .toList();
    }
}
