package org.techm.capstone.restaurantManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techm.capstone.restaurantManagementSystem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByUsername(String username);
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	List<User> findByRole(String role);
}