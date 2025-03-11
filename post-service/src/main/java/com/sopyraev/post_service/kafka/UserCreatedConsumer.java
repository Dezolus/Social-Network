package com.sopyraev.post_service.kafka;

import com.sopyraev.post_service.model.Post;
import com.sopyraev.post_service.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
public class UserCreatedConsumer {

    @Autowired
    private PostRepository postRepository;


    @KafkaListener(topics = "user_created", groupId = "${spring.kafka.consumer.group-id}", containerGroup = "${spring.kafka.bootstrap-servers}")
    public void listenUserCreatedEvent(String message) {
        System.out.println("Received user-created event: " + message);
        Post post = new Post();
        post.setCreatedAt(Instant.now());
        post.setUserId(2L); // TODO заменить хардкод на ссылку на userId
        post.setContent("Im Kafka Hello!");
        postRepository.save(post);
    }
}
