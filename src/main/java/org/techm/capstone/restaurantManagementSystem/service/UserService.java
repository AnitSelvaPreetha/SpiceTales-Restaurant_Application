package org.techm.capstone.restaurantManagementSystem.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.techm.capstone.restaurantManagementSystem.exception.InvalidLoginException;
import org.techm.capstone.restaurantManagementSystem.exception.UserAlreadyExistsException;
import org.techm.capstone.restaurantManagementSystem.model.User;
import org.techm.capstone.restaurantManagementSystem.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void register(User user) {


        if(userRepository.findByUsername(user.getUsername()).isPresent()) {

            throw new UserAlreadyExistsException("Username already registered!");
        }

		user.setRole("CUSTOMER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	public boolean existsByUsername(String username) {
		return userRepository.findByUsername(username).isPresent();
		}

    public boolean authenticate(String username, String rawPassword) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new InvalidLoginException("Invalid username or password!");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidLoginException("Invalid username or password!");
        }
        return true;
    }
	public boolean changePassword(String username, String oldPassword, String newPassword) {
		Optional<User> optionalUser = userRepository.findByUsername(username);


		if (optionalUser.isPresent()) {
		User user = optionalUser.get();
		if (passwordEncoder.matches(oldPassword, user.getPassword())) {
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		return true;
		}
		}

		return false; // Incorrect old password or user not found
		}

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

		@Transactional
		public Optional<String> createStaff(User user) {
			if (user == null) {
				return Optional.of("User data is missing");
			}

			// basic validations
			if (user.getUsername() == null || user.getUsername().trim().length() < 3) {
				return Optional.of("Username must be at least 3 characters");
			}
			if (user.getPassword() == null || user.getPassword().length() < 6) {
				return Optional.of("Password must be at least 6 characters");
			}
			if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
				return Optional.of("Email is required");
			}

			// uniqueness checks
			if (userRepository.findByUsername(user.getUsername()).isPresent()) {
				return Optional.of("Username already exists");
			}
			if (userRepository.findByEmail(user.getEmail()).isPresent()) {
				return Optional.of("Email already in use");
			}

			// set role and encode password
			user.setRole("STAFF");
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			userRepository.save(user);
			return Optional.empty();
		}
	@Transactional
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}
	@Transactional
	public List<User> getAllStaff() {
		return userRepository.findByRole("STAFF");
	}

	@Transactional
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	}

