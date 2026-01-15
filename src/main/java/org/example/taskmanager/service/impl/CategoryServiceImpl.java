package org.example.taskmanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.taskmanager.dto.CategoryRequestDto;
import org.example.taskmanager.dto.CategoryResponseDto;
import org.example.taskmanager.exception.CategoryAlreadyExistsException;
import org.example.taskmanager.exception.CategoryNotFoundException;
import org.example.taskmanager.mapper.CategoryMapper;
import org.example.taskmanager.model.Category;
import org.example.taskmanager.repository.CategoryRepository;
import org.example.taskmanager.service.CategoryService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }
    
    @Override
    @CacheEvict(value = {"categories", "categoryById"}, allEntries = true)
    public CategoryResponseDto create(CategoryRequestDto dto) {
        log.info("Creating category: name={}",
                 dto.getName());
        
        if (categoryRepository.existsByName(dto.getName())) {
            throw new CategoryAlreadyExistsException(dto.getName());
        }
        
        Category category = categoryMapper.toEntity(dto);
        
        Category saved = categoryRepository.save(category);
        log.info("Category created successfully with id={}", saved.getId());
        return categoryMapper.toDto(saved);
    }
    
    @Override
    @Cacheable("categories")
    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll()
                       .stream()
                       .map(categoryMapper::toDto)
                       .toList();
    }
    
    @Override
    @Cacheable(value = "categoryById", key = "#id")
    public CategoryResponseDto getById(Long id) {
        log.info("Fetching category from DB, id={}", id);
        Category category = categoryRepository.findById(id)
                                    .orElseThrow(() -> new CategoryNotFoundException(id));
        return categoryMapper.toDto(category);
    }
    
    @Override
    @CacheEvict(value = {"categories", "categoryById"}, allEntries = true)
    public CategoryResponseDto update(Long id, CategoryRequestDto dto) {
        Category category = categoryRepository.findById(id)
                                              .orElseThrow(() -> new CategoryNotFoundException(id));
        
        if (!category.getName().equals(dto.getName())
                    && categoryRepository.existsByName(dto.getName())) {
            throw new CategoryAlreadyExistsException(dto.getName());
        }
        
        category.setName(dto.getName());
        return categoryMapper.toDto(categoryRepository.save(category));
    }
    
    @Override
    @CacheEvict(value = {"categories", "categoryById"}, allEntries = true)
    public void delete(Long id) {
        log.info("Deleting category with id={}", id);
        
        Category category = categoryRepository.findById(id)
                                              .orElseThrow(() -> new CategoryNotFoundException(id));
        
        if (!category.getTasks().isEmpty()) {
            throw new IllegalStateException("Cannot delete category with existing tasks");
        }
        
        categoryRepository.delete(category);
    }
}
