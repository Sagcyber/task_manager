package org.example.taskmanager.model;

import java.time.LocalDate;

public class Task {
    
    private static long counter = 1;
    private long id;
    private String taskName;
    private TaskStatus status;
    private LocalDate deadline;
    
    public Task(){
    
    }
    
    public Task(String taskName, TaskStatus status, LocalDate deadline) {
        this.id = counter++;
        this.taskName = taskName;
        this.status = status;
        this.deadline = deadline;
    }
    
    public long getId() {
        return id;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    public TaskStatus getStatus() {
        return status;
    }
    
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    
    public LocalDate getDeadline() {
        return deadline;
    }
    
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    
}
