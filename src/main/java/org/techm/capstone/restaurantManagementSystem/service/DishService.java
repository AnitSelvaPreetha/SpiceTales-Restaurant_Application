package org.techm.capstone.restaurantManagementSystem.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.techm.capstone.restaurantManagementSystem.model.Dish;
import org.techm.capstone.restaurantManagementSystem.model.Dish.DishCategory;
import org.techm.capstone.restaurantManagementSystem.repository.DishRepository;
import org.techm.capstone.restaurantManagementSystem.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class DishService {

	@Autowired
	private DishRepository dishRepository;

	@Autowired
	private OrderRepository orderRepository;

	// existing methods kept/normalized
	public List<Dish> getAllDishes() {
		return dishRepository.findAll();
	}

	public List<Dish> findAll() {
		return getAllDishes();
	}

	public void addDish(Dish dish) {
		dishRepository.save(dish);
	}

	public void saveDish(Dish dish) {
		dishRepository.save(dish);
	}

	public Dish getDishById(Long id) {
		return dishRepository.findById(id).orElseThrow(() -> new RuntimeException("Dish not found"));
	}

	public void updateDish(Dish dish) {
		dishRepository.save(dish);
	}

	public List<Dish> getDishesByCategory(DishCategory category) {
		if (category == null) return Collections.emptyList();
		return dishRepository.findByCategory(category);
	}

	public List<Dish> findByCategoryName(String categoryName) {
		if (categoryName == null || categoryName.isBlank()) return findAll();
		try {
			DishCategory cat = DishCategory.valueOf(categoryName);
			return getDishesByCategory(cat);
		} catch (IllegalArgumentException ex) {
			return Collections.emptyList();
		}
	}

	// New search method (global search across name and description)
	public List<Dish> search(String q) {
		if (q == null || q.isBlank()) return findAll();
		return dishRepository.searchByNameOrDescription(q.trim());
	}

	// Provide categories for UI (enum names)
	public List<String> listAllCategories() {
		return Arrays.stream(Dish.DishCategory.values())
				.map(Enum::name)
				.collect(Collectors.toList());
	}

	public long countDishes() {
		return dishRepository.count();
	}

	@Transactional
	public void deleteDish(Long dishId) {
		orderRepository.deleteDishMappings(dishId); // remove mappings if you have such a method
		dishRepository.deleteById(dishId);
	}

	public void deleteById(Long id) {
		dishRepository.deleteById(id);
	}
}
