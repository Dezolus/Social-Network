package com.sopyraev.comment_service.service;

import com.sopyraev.comment_service.model.Comment;
import com.sopyraev.comment_service.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public Comment createComment(Comment comment) {
        comment.setCreatedAt(Instant.now());
        Comment savedComment = commentRepository.save(comment);

        sendCommentCreatedEvent(savedComment);
        
        return savedComment;
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    private void sendCommentCreatedEvent(Comment comment) {
        String commentEvent = String.format("{\"id\": %d, \"userId\": %d, \"postId\": %d, \"content\": \"%s\", \"createdAt\": \"%s\"}",
            comment.getId(), comment.getUserId(), comment.getPostId(), comment.getContent(), comment.getCreatedAt());
        kafkaTemplate.send("comments_topic", commentEvent);
    }
}
