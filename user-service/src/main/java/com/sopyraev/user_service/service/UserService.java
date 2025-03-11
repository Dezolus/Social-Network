package com.sopyraev.user_service.service;

import com.sopyraev.user_service.model.User;
import com.sopyraev.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    public User createUser(User user) {
        user.setCreatedAt(Instant.now());
        User savedUser = userRepository.save(user);
        sendUserCreateEvent(user);
        return savedUser;
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private void sendUserCreateEvent(User user) {
        String userEvent = String.format("{\"id\": \"%s\", \"username\": \"%s\", \"email\": \"%s\"}",
            user.getId(), user.getUsername(), user.getEmail());
        kafkaTemplate.send("user_created", userEvent);
    }
}
