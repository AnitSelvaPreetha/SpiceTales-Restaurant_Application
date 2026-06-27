package org.techm.capstone.restaurantManagementSystem.controller;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.techm.capstone.restaurantManagementSystem.model.Dish;
import org.techm.capstone.restaurantManagementSystem.model.Dish.DishCategory;
import org.techm.capstone.restaurantManagementSystem.model.Order;
import org.techm.capstone.restaurantManagementSystem.model.User;
import org.techm.capstone.restaurantManagementSystem.repository.UserRepository;
import org.techm.capstone.restaurantManagementSystem.service.DishService;
import org.techm.capstone.restaurantManagementSystem.service.OrderService;
import org.techm.capstone.restaurantManagementSystem.service.ReservationService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private DishService dishService;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Menu with optional search and category filter:
	 * - /customer/menu                -> show all categories grouped
	 * - /customer/menu?q=term         -> global search across name/description
	 * - /customer/menu?category=INDIAN -> show only that category
	 * - /customer/menu?category=INDIAN&q=term -> search inside selected category
	 */
	@GetMapping("/menu")
	public String browseMenu(
			@RequestParam(required = false) String q,
			@RequestParam(required = false) String category,
			Model model) {

		String query = (q == null) ? "" : q.trim();
		model.addAttribute("q", query);
		model.addAttribute("categories", dishService.listAllCategories());
		model.addAttribute("selectedCategory", category);

		List<Dish> dishes;

		if (category != null && !category.isBlank()) {
			// category-specific view (optionally filter by q)
			dishes = dishService.findByCategoryName(category);
			if (!query.isBlank()) {
				String term = query.toLowerCase();
				dishes = dishes.stream()
						.filter(d -> d.getName().toLowerCase().contains(term)
								|| (d.getDescription() != null && d.getDescription().toLowerCase().contains(term)))
						.collect(Collectors.toList());
			}

			Map<DishCategory, List<Dish>> map = new LinkedHashMap<>();
			try {
				map.put(DishCategory.valueOf(category), dishes);
			} catch (IllegalArgumentException ex) {
				map = dishes.stream()
						.collect(Collectors.groupingBy(Dish::getCategory, LinkedHashMap::new, Collectors.toList()));
			}
			map.entrySet().removeIf(e -> e.getValue() == null || e.getValue().isEmpty());
			model.addAttribute("dishesByCategory", map);
			return "customer/menu";
		}

		// no category: global search or show all grouped by category
		if (!query.isBlank()) {
			dishes = dishService.search(query);
		} else {
			dishes = dishService.findAll();
		}

		Map<DishCategory, List<Dish>> grouped = dishes.stream()
				.collect(Collectors.groupingBy(Dish::getCategory, LinkedHashMap::new, Collectors.toList()));
		grouped.entrySet().removeIf(e -> e.getValue() == null || e.getValue().isEmpty());

		model.addAttribute("dishesByCategory", grouped);
		return "customer/menu";
	}

	@PostMapping("/order")
	public String placeOrder(@RequestParam List<Long> dishIds,
							 @RequestParam List<Integer> quantities,
							 @RequestParam int tableNumber,
							 Principal principal,
							 RedirectAttributes redirectAttributes) {
		String username = principal.getName();
		Order order = orderService.placeOrder(dishIds, quantities, username, tableNumber);
		redirectAttributes.addFlashAttribute("order", order);
		return "redirect:/customer/orders";
	}

	@GetMapping("/orders")
	public String viewLatestOrder(Model model, Principal principal) {
		String username = principal.getName();
		User user = userRepository.findByUsername(username).orElseThrow();

		Order latestOrder = orderService.getLatestOrderForCustomer(user.getId());

		if (latestOrder != null && !latestOrder.getItems().isEmpty() && latestOrder.getTotalPrice() > 0) {
			model.addAttribute("orders", List.of(latestOrder));
		} else {
			model.addAttribute("orders", List.of());
		}

		return "customer/orders";
	}

	@GetMapping("/profile")
	public String showProfile(Model model, Principal principal) {
		String username = principal.getName();
		User user = userRepository.findByUsername(username).orElseThrow();

		List<Order> allOrders = orderService.getOrdersForUser(username);
		List<Order> validOrders = allOrders.stream()
				.filter(order -> !order.getItems().isEmpty() && order.getTotalPrice() > 0)
				.collect(Collectors.toList());

		model.addAttribute("user", user);
		model.addAttribute("pastOrders", validOrders);
		model.addAttribute("reservations", reservationService.fetchReservationsByUser());

		return "customer/profile";
	}

	@GetMapping("/bill/{id}")
	public String viewOrderBill(@PathVariable("id") Long orderId, Model model) {
		Order order = orderService.getOrderById(orderId);
		model.addAttribute("order", order);
		return "customer/order-details";
	}
}