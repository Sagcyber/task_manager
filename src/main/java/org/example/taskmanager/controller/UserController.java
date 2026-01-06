package org.example.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.taskmanager.dto.UserRequestDto;
import org.example.taskmanager.dto.UserResponseDto;
import org.example.taskmanager.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Users management API")
@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @Operation(summary = "Create user")
    @PostMapping
    public UserResponseDto createUser(@RequestBody
                                      UserRequestDto dto) {
        return userService.createUser(dto);
    }
    
    @Operation(summary = "Get all users")
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }
}
