package org.techm.capstone.restaurantManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techm.capstone.restaurantManagementSystem.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
