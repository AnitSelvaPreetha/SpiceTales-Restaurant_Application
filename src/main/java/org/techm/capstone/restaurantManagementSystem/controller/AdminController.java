package org.techm.capstone.restaurantManagementSystem.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.techm.capstone.restaurantManagementSystem.model.Dish;
import org.techm.capstone.restaurantManagementSystem.model.Dish.DishCategory;
import org.techm.capstone.restaurantManagementSystem.model.Order;
import org.techm.capstone.restaurantManagementSystem.model.OrderItem;
import org.techm.capstone.restaurantManagementSystem.model.User;
import org.techm.capstone.restaurantManagementSystem.repository.UserRepository;
import org.techm.capstone.restaurantManagementSystem.service.DishService;
import org.techm.capstone.restaurantManagementSystem.service.OrderService;
import org.techm.capstone.restaurantManagementSystem.service.ReservationService;
import org.techm.capstone.restaurantManagementSystem.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private DishService dishService;
	@Autowired
	private OrderService orderService;

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private ReservationService reservationService;

	@GetMapping("/menu")
	public String adminMenu(Model model) {
		model.addAttribute("dish", new Dish());
		model.addAttribute("dishes", dishService.getAllDishes());
		return "admin/menu";
	}

	@PostMapping("/addDish")
	public String addDish(@RequestParam String name, @RequestParam String description, @RequestParam double price, @RequestParam DishCategory category,
			@RequestParam(value="image") MultipartFile image) {
		Dish dish;
		try {
			dish = new Dish(name, description, price, category, image.getBytes());
			dishService.saveDish(dish);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/admin/menu"; 
	}
	
	@GetMapping("/orders")
	public String viewAllOrders(Model model) {
		List<Order> allOrders = orderService.getAllOrders();
		List<Order> validOrders = allOrders.stream()
		        .filter(order -> order.getItems()!=null && !order.getItems().isEmpty())
		        .collect(Collectors.toList());
	model.addAttribute("orders", allOrders);
	return "admin/orders";
	}
	@GetMapping("/orders/{id}")
	public String viewOrderDetails(@PathVariable Long id, Model model) {
	Order order = orderService.getOrderById(id);
	model.addAttribute("order", order);
	model.addAttribute("dishes", order.getItems());
	model.addAttribute("total", order.getItems().stream().mapToDouble(OrderItem::getPrice).sum());
	return "admin/order-details";
	}
	@GetMapping("/admin/menu")
	public String showAdminMenu(Model model) {
	model.addAttribute("dishes", dishService.getAllDishes());
	return "admin/menu";
	}
	@PostMapping("/menu/update")
	public String updateDish(@ModelAttribute Dish dish) {
	dishService.updateDish(dish); // Create this method in DishService
	return "redirect:/admin/menu";
	}
	
	@GetMapping("/menu/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model) {
	Dish dish = dishService.getDishById(id);
	model.addAttribute("dish", dish);
	model.addAttribute("categories",DishCategory.values());
	return "admin/edit-dish"; // Points to edit-dish.html
	}
	@PostMapping("/menu/edit/{id}")
	public String updateDish(@PathVariable("id") Long id, @ModelAttribute Dish updatedDish) throws IOException{
	Dish dish = dishService.getDishById(id);
    //Dish.addAttribute("dish", updatedDish);
    if(dish!=null) {
        dish.setName(updatedDish.getName());
        dish.setDescription(updatedDish.getDescription());
        dish.setPrice(updatedDish.getPrice());
        if (updatedDish.getImage() != null && updatedDish.getImage().length>0) {
            dish.setImage(updatedDish.getImage());
        }

        dishService.saveDish(dish);
    }
	return "redirect:/admin/menu";
	}
	@PostMapping("/menu/delete/{id}")
	public String deleteDish(@PathVariable Long id) {
	dishService.deleteById(id);
	return "redirect:/admin/menu";
	}
	
	@GetMapping("/bill/{id}")
    public String viewOrderBill(@PathVariable("id") Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        return "admin/order-details"; // this must match the name of your Thymeleaf file: order-details.html
    }
	
	@GetMapping("/reservations")
	public String viewReservations(Model model) {
		model.addAttribute("reservations", reservationService.fetchReservations());
		return "admin/admin-reservation";
	}

	@GetMapping("/staff/new")
	public String showAddStaffForm(Model model) {
		model.addAttribute("user", new User());
		return "admin/add-staff";
	}

	@PostMapping("/staff/new")
	public String addStaff(@ModelAttribute("user") User user, Model model) {
		// Use UserService which performs validation, uniqueness checks and saves
		var result = userService.createStaff(user);
		if (result.isPresent()) {
			model.addAttribute("error", result.get());
			model.addAttribute("user", user);
			return "admin/add-staff";
		}
		return "redirect:/admin/menu";
	}

	// Optionally add a staff list view
	@GetMapping("/staff")
	public String listStaff(Model model) {
		model.addAttribute("staffs", userRepository.findAll()
				.stream()
				.filter(u -> "STAFF".equals(u.getRole()))
				.toList());
		return "admin/staff-list";
	}
	@GetMapping("/staff/{id}")
	public String viewStaff(@PathVariable Long id, Model model) {
		Optional<User> userOpt = userService.findById(id);
		if (userOpt.isEmpty()) {
			return "redirect:/admin/staff";
		}
		model.addAttribute("user", userOpt.get());
		return "admin/staff-view";
	}

	// delete staff
	@PostMapping("/staff/delete/{id}")
	public String deleteStaff(@PathVariable Long id) {
		userService.deleteById(id);
		return "redirect:/admin/staff";
	}
}