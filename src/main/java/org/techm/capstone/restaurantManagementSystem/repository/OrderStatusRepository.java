package org.techm.capstone.restaurantManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techm.capstone.restaurantManagementSystem.model.OrderStatus;
import java.util.List;
import java.util.Optional;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    List<OrderStatus> findAllByOrderByCreatedAtAsc();
    List<OrderStatus> findByStatusOrderByCreatedAtAsc(OrderStatus.Status status);
    Optional<OrderStatus> findFirstByStatusOrderByCreatedAtAsc(OrderStatus.Status status);
    // optional helper to fetch active (not COMPLETE)
    List<OrderStatus> findByStatusNotOrderByCreatedAtAsc(OrderStatus.Status status);
}
