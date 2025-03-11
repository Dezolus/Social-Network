package com.sopyraev.user_service.kafka;

import com.sopyraev.user_service.model.User;
import com.sopyraev.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PostCreatedConsumer {

    @Autowired
    private UserRepository userRepository;

    @KafkaListener(topics = "post_created", groupId = "${spring.kafka.consumer.group-id}", containerGroup = "${spring.kafka.bootstrap-servers}")
    public void listenPostCreatedEvent(String message) {
        System.out.println("Received post-created event: " + message);
        User userFromKafka = new User();
        userFromKafka.setCreatedAt(Instant.now());
        userFromKafka.setUsername("Kafka User");
        userFromKafka.setEmail("kafka@mail.com");
        userFromKafka.setPassword("KKK");
        userRepository.save(userFromKafka);
    }
}
