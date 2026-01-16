package org.example.taskmanager.kafka;

public record TaskCreatedEvent(
        Long taskId,
        String taskName,
        String categoryName) {
}