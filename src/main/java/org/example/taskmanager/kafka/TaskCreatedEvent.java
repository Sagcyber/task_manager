package org.example.taskmanager.kafka;

public record TaskCreatedEvent(
        Long taskId,
        Long userId,
        String title) {
}

