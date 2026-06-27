package org.techm.capstone.restaurantManagementSystem.service;

//package org.techm.capstone.restaurantManagementSystem.service;

//package org.techm.capstone.restaurantManagementSystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.techm.capstone.restaurantManagementSystem.model.User;
import org.techm.capstone.restaurantManagementSystem.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  private User testUser;

  @BeforeEach
  void setUp() {
      MockitoAnnotations.openMocks(this);

      testUser = new User();
      testUser.setId(1L);
      testUser.setUsername("john");
      testUser.setPassword("plainPassword");
  }

  @Test
  void testRegister() {
      when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

      userService.register(testUser);

      assertEquals("CUSTOMER", testUser.getRole());
      assertEquals("encodedPassword", testUser.getPassword());
      verify(userRepository).save(testUser);
  }

  @Test
  void testExistsByUsername_True() {
      when(userRepository.findByUsername("john")).thenReturn(Optional.of(testUser));

      boolean exists = userService.existsByUsername("john");

      assertTrue(exists);
  }

  @Test
  void testExistsByUsername_False() {
      when(userRepository.findByUsername("jane")).thenReturn(Optional.empty());

      boolean exists = userService.existsByUsername("jane");

      assertFalse(exists);
  }

  @Test
  void testChangePassword_Success() {
      when(userRepository.findByUsername("john")).thenReturn(Optional.of(testUser));
      when(passwordEncoder.matches("oldPassword", "hashedOldPassword")).thenReturn(true);
      when(passwordEncoder.encode("newPassword")).thenReturn("hashedNewPassword");

      testUser.setPassword("hashedOldPassword");

      boolean result = userService.changePassword("john", "oldPassword", "newPassword");

      assertTrue(result);
      verify(userRepository).save(testUser);
      assertEquals("hashedNewPassword", testUser.getPassword());
  }

  @Test
  void testChangePassword_Failure_WrongOldPassword() {
      testUser.setPassword("hashedOldPassword");
      when(userRepository.findByUsername("john")).thenReturn(Optional.of(testUser));
      when(passwordEncoder.matches("wrongOldPassword", "hashedOldPassword")).thenReturn(false);

      boolean result = userService.changePassword("john", "wrongOldPassword", "newPassword");

      assertFalse(result);
      verify(userRepository, never()).save(any());
  }

  @Test
  void testChangePassword_UserNotFound() {
      when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

      boolean result = userService.changePassword("unknown", "any", "newPassword");

      assertFalse(result);
  }
}


