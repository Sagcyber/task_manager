package org.example.taskmanager.service;

import org.example.taskmanager.dto.UserRequestDto;
import org.example.taskmanager.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    
    UserResponseDto createUser(UserRequestDto dto);
    
    List<UserResponseDto> getAllUsers();
    
    UserResponseDto getById(Long id);
    
    UserResponseDto update(Long id, UserRequestDto dto);
    
    void delete(Long id);
}
