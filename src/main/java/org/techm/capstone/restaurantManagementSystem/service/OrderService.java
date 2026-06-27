package org.techm.capstone.restaurantManagementSystem.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.techm.capstone.restaurantManagementSystem.model.*;
import org.techm.capstone.restaurantManagementSystem.repository.DishRepository;
import org.techm.capstone.restaurantManagementSystem.repository.OrderRepository;
import org.techm.capstone.restaurantManagementSystem.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DishRepository dishRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DishService dishService;
 @Autowired
 private OrderStatusService orderStatusService;

	@Transactional
	public Order placeOrder(List<Long> dishIds, List<Integer> quantities, String username,int tableName) {
		User customer = userRepository.findByUsername(username).orElseThrow();
		Order order = new Order();
		order.setCustomer(customer);
		order.setOrderTime(LocalDateTime.now());
		order.setTableNumber(tableName);
		// Save order to generate ID
		order = orderRepository.save(order);

		// Map dishId to total quantity
		Map<Long, Integer> dishIdToQuantity = new HashMap<>();
		for (int i = 0; i < dishIds.size(); i++) {
			Long dishId = dishIds.get(i);
			int qty = quantities.get(i);
			dishIdToQuantity.put(dishId, dishIdToQuantity.getOrDefault(dishId, 0) + qty); // add quantity if duplicate
		}

		List<OrderItem> orderItems = new ArrayList<>();
		for (Map.Entry<Long, Integer> entry : dishIdToQuantity.entrySet()) {
			Long dishId = entry.getKey();
			int qty = entry.getValue();

			Dish dish = dishService.getDishById(dishId);
			OrderItem item = new OrderItem();
			item.setDishName(dish.getName());
			item.setPrice(dish.getPrice()); // total price for this item
			item.setQuantity(qty);
			item.setOrder(order);

			orderItems.add(item);
		}

		order.setItems(orderItems);
		order.setTotalPrice(order.calculateTotalPrice());

		orderRepository.save(order); // save again with items
        orderStatusService.populateFromOrder(order);
		return order;
	}

	public Order getLatestOrderForCustomer(Long customerId) {
		return orderRepository.findTopByCustomerIdOrderByOrderTimeDesc(customerId);
	}

	public List<Order> getOrdersForUser(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found:" + username));
		List<Order> orders = orderRepository.findByCustomer(user);
		for (Order order : orders) {
			double total = order.getItems().stream().mapToDouble(OrderItem::getPrice).sum();
			order.setTotalPrice(total);
		}
		return orders;
	}
	public List<Order> getAllOrders(){
		return orderRepository.findAll();
	}
	public Order getOrderById(Long id) {
		return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("order not found"));
	}
	

	public long countOrders() {
	    return orderRepository.count();
	}
}