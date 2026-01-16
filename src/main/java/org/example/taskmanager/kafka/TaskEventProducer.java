package org.example.taskmanager.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskEventProducer {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    public void sendTaskCreated(TaskCreatedEvent event) {
        kafkaTemplate.send("task.created", event);
    }
}
