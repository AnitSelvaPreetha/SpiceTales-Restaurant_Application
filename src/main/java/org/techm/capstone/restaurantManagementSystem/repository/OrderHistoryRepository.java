package org.techm.capstone.restaurantManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techm.capstone.restaurantManagementSystem.model.OrderHistory;
import java.util.List;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> findAllByOrderByCompletedAtDesc();
}
