package org.techm.capstone.restaurantManagementSystem.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.techm.capstone.restaurantManagementSystem.model.User;
import org.techm.capstone.restaurantManagementSystem.repository.UserRepository;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
//@RunWith(MokitoJUnitRunner.class)
public class CustomUserDetailsServiceTest {
	//@ExtendWith(MockitoExtension.class)
	//class CustomUserDetailsServiceTest {

	    @Mock
	    private UserRepository userRepository;

	    @InjectMocks
	    private CustomUserDetailsService userDetailsService;

	    @Test
	    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
	        User user = new User();
	        user.setUsername("john");
	        user.setPassword("pass");
	        user.setRole("ADMIN");

	        Mockito.when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

	        UserDetails userDetails = userDetailsService.loadUserByUsername("john");

	        assertNotNull(userDetails);
	        assertEquals("john", userDetails.getUsername());
	        assertEquals("pass", userDetails.getPassword());
	    }

	    @Test
	    void loadUserByUsername_ShouldThrowException_WhenUserDoesNotExist() {
	        Mockito.when(userRepository.findByUsername("notfound")).thenReturn(Optional.empty());

	        assertThrows(UsernameNotFoundException.class, () -> {
	            userDetailsService.loadUserByUsername("notfound");
	        });
	    }
	}



