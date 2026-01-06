package org.example.taskmanager.controller;

import org.example.taskmanager.dto.UserRequestDto;
import org.example.taskmanager.dto.UserResponseDto;
import org.example.taskmanager.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping
    public UserResponseDto createUser(@RequestBody
                                      UserRequestDto dto) {
        return userService.createUser(dto);
    }
    
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }
}
