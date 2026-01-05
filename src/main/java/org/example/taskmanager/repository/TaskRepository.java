package org.example.taskmanager.repository;

import org.example.taskmanager.model.Task;
import org.example.taskmanager.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByCategory_Name(String name);
    List<Task> findByStatus(TaskStatus status);
}
