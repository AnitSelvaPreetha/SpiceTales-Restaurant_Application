package org.techm.capstone.restaurantManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.techm.capstone.restaurantManagementSystem.model.Order;
import org.techm.capstone.restaurantManagementSystem.model.User;

import jakarta.transaction.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByCustomer(User user);
	Order findTopByCustomerIdOrderByOrderTimeDesc(Long customerId);
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM orders_dishes WHERE dish_id = :dishId", nativeQuery = true)
	void deleteDishMappings(@Param("dishId") Long dishId);
}
