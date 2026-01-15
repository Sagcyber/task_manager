package org.example.taskmanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.taskmanager.dto.UserRequestDto;
import org.example.taskmanager.dto.UserResponseDto;
import org.example.taskmanager.exception.UserAlreadyExistsException;
import org.example.taskmanager.exception.UserNotFoundException;
import org.example.taskmanager.mapper.UserMapper;
import org.example.taskmanager.model.User;
import org.example.taskmanager.repository.UserRepository;
import org.example.taskmanager.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    
    @Override
    @CacheEvict(value = {"users", "userById"}, allEntries = true)
    public UserResponseDto createUser(UserRequestDto dto) {
        log.info("Creating user: name={}", dto.getUsername());
        
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException(dto.getUsername());
        }
        
        User user = userMapper.toEntity(dto);
        return userMapper.toDto(userRepository.save(user));
    }
    
    @Override
    @Cacheable("users")
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                       .stream()
                       .map(userMapper::toDto)
                       .toList();
    }
    
    @Override
    @Cacheable(value = "userById", key = "#id")
    public UserResponseDto getById(Long id) {
        log.info("Fetching user from DB, id={}", id);
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new UserNotFoundException(id));
        
        return userMapper.toDto(user);
    }
    
    @Override
    @CacheEvict(value = {"users", "userById"}, allEntries = true)
    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new UserNotFoundException(id));
        
        if (!user.getUsername().equals(dto.getUsername())
                    && userRepository.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException(dto.getUsername());
        }
        
        user.setUsername(dto.getUsername());
        return userMapper.toDto(userRepository.save(user));
    }
    
    @Override
    @CacheEvict(value = {"users", "userById"}, allEntries = true)
    public void delete(Long id) {
        log.info("Deleting user with id={}", id);
        
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
