package org.example.taskmanager.dto;

import org.example.taskmanager.model.TaskStatus;

import java.time.LocalDate;

public class TaskRequestDto {
    
    private String taskName;
    private TaskStatus status;
    private LocalDate deadline;
    private String categoryName;

}
