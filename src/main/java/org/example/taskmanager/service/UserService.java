package org.example.taskmanager.service;

import org.example.taskmanager.dto.UserRequestDto;
import org.example.taskmanager.dto.UserResponseDto;
import org.example.taskmanager.mapper.UserMapper;
import org.example.taskmanager.model.User;
import org.example.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = userMapper.toEntity(dto);
        return userMapper.toDto(userRepository.save(user));
    }
    
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                       .stream()
                       .map(userMapper::toDto)
                       .toList();
    }
}
