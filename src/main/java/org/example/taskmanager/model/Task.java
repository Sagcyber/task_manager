package org.example.taskmanager.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String taskName;
    
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    
    private LocalDate deadline;
    
    @Enumerated(EnumType.STRING)
    private DELETE category;
    
    public Task(){
    
    }
    
    public Task(String taskName, TaskStatus status, LocalDate deadline, DELETE category) {
        this.taskName = taskName;
        this.status = status;
        this.deadline = deadline;
        this.category = category;
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
    
    public DELETE getCategory() {
        return category;
    }
    
    public void setCategory(DELETE category) {
        this.category = category;
    }
    
}
