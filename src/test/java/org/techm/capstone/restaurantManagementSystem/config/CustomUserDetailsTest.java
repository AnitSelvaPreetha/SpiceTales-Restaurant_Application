package org.techm.capstone.restaurantManagementSystem.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.techm.capstone.restaurantManagementSystem.model.User;

public class CustomUserDetailsTest {
	@Test
	void testCustomUserDetails() {
	    User user = new User();
	    user.setUsername("john");
	    user.setPassword("password");
	    user.setRole("ADMIN");

	    CustomUserDetails userDetails = new CustomUserDetails(user);

	    assertEquals("john", userDetails.getUsername());
	    assertEquals("password", userDetails.getPassword());
	    assertTrue(userDetails.getAuthorities().stream()
	        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
	}


}
