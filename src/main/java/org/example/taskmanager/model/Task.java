package org.example.taskmanager.model;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Setter
    @Column(nullable = false)
    private String taskName;
    
    @Setter
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    
    @Setter
    private LocalDate deadline;
    
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    public Task() {
    }
    
    public Task(String taskName, TaskStatus status, LocalDate deadline, Category category) {
        this.taskName = taskName;
        this.status = status;
        this.deadline = deadline;
        this.category = category;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public TaskStatus getStatus() {
        return status;
    }
    
    public LocalDate getDeadline() {
        return deadline;
    }
    
    public Category getCategory() {
        return category;
    }
    
}
