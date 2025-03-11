package com.sopyraev.post_service.service;

import com.sopyraev.post_service.client.UserServiceClient;
import com.sopyraev.post_service.model.Post;
import com.sopyraev.post_service.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public Post createPost(Post post) {
        if (!userServiceClient.userExists(post.getUserId())) {
            throw new IllegalArgumentException("User does not exist");
        }
        post.setCreatedAt(Instant.now());
        Post saved = postRepository.save(post);
        sendPostCreatedEvent(saved);
        return saved;
    }

    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    public List<Post> getPostByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    private void sendPostCreatedEvent(Post createdPost) {
        String postEvent = String.format("{\"id\":%d, \"userId\":%d, \"createdAt\":%s, \"content\":%s}",
            createdPost.getId(), createdPost.getUserId(), createdPost.getCreatedAt(), createdPost.getContent());
        kafkaTemplate.send("post_created", postEvent);
    }
}
