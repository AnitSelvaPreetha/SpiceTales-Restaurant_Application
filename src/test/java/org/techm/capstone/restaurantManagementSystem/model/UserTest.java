package org.techm.capstone.restaurantManagementSystem.model;

//package org.techm.capstone.restaurantManagementSystem.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validUser_ShouldPassValidation() {
        User user = new User();
        user.setUsername("john123");
        user.setPassword("Pass@123");
        user.setRole("CUSTOMER");
        user.setEmail("john@example.com");
        user.setPhone("1234567890");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Expected no validation errors");
    }

    @Test
    void invalidUsername_ShouldFailValidation() {
        User user = new User();
        user.setUsername(""); // Invalid
        user.setPassword("Pass@123");
        user.setRole("ADMIN");
        user.setEmail("test@example.com");
        user.setPhone("1234567890");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @Test
    void invalidPassword_ShouldFailValidation() {
        User user = new User();
        user.setUsername("john123");
        user.setPassword("pass"); // Doesn't meet pattern
        user.setRole("ADMIN");
        user.setEmail("test@example.com");
        user.setPhone("1234567890");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

   
}

