package org.example.taskmanager.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @OneToMany(mappedBy = "category")
    private List<Task> tasks = new ArrayList<>();
    
    public Category() {
    }
    
    public Category(String name) {
        this.name = name;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public List<Task> getTasks() {
        return tasks;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
