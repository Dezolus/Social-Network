package com.sopyraev.user_service;

import com.sopyraev.user_service.model.User;
import com.sopyraev.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setUsername("test");
		user.setEmail("test@test.com");
		user.setPassword("test");

		User createdUser = userService.createUser(user);
		assertNotNull(createdUser.getId());
	}

}
