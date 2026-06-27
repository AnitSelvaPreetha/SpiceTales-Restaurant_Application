package org.techm.capstone.restaurantManagementSystem.service;

//package org.techm.capstone.restaurantManagementSystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.techm.capstone.restaurantManagementSystem.model.Dish;
import org.techm.capstone.restaurantManagementSystem.model.Dish.DishCategory;
import org.techm.capstone.restaurantManagementSystem.model.Order;
import org.techm.capstone.restaurantManagementSystem.model.OrderItem;
import org.techm.capstone.restaurantManagementSystem.repository.DishRepository;
import org.techm.capstone.restaurantManagementSystem.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class DishServiceTest {

    @Mock
    private DishRepository dishRepository;
    
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private DishService dishService;
    
    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDishes() {
        List<Dish> dishes = List.of(new Dish(), new Dish());
        when(dishRepository.findAll()).thenReturn(dishes);

        List<Dish> result = dishService.getAllDishes();
        assertEquals(2, result.size());
        verify(dishRepository).findAll();
    }
    
    @Test
    void testAddDish() {
        Dish dish = new Dish();
        dishService.addDish(dish);
        verify(dishRepository).save(dish);
    }

//    @Test
//    void testDeleteDish() {
//        Dish dish = new Dish();
//    	OrderItem orderItem = new OrderItem();
//    	orderItem.setId(1L);
//    	
//    	List<OrderItem> expectedOrderItems = Arrays.asList(orderItem);
//
//        Order order = new Order();
//       // Dish items = new ArrayList<>();
//       // dish.add(orderItem);
//        order.setItems(expectedOrderItems);
//       
//
//      //  when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
//        when(orderRepository.findAll()).thenReturn(List.of(order));
//
//        dishService.deleteDish(1L);
//
//        assertFalse(order.getItems().contains(dish));
//        verify(orderRepository).saveAll(anyList());
//        verify(dishRepository).deleteById(1L);
//    }

    @Test
    void testSaveDish() {
        Dish dish = new Dish();
        dishService.saveDish(dish);
        verify(dishRepository).save(dish);
    }

    @Test
    void testGetDishById_Found() {
        Dish dish = new Dish();
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));

        Dish result = dishService.getDishById(1L);
        assertNotNull(result);
    }

    @Test
    void testGetDishById_NotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dishService.getDishById(1L));

        assertEquals("Dish not found", exception.getMessage());
    }

    
    @Test
    void testUpdateDish() {
        Dish dish = new Dish();
        dishService.updateDish(dish);
        verify(dishRepository).save(dish);
    }

    @Test
    public void testGetDishesByCategory() {
        // Given
        DishCategory category = DishCategory.INDIAN;

        Dish dish1 = new Dish();
        dish1.setId(1L);
        dish1.setName("Paneer Butter Masala");
        dish1.setDescription("Creamy paneer dish");
        dish1.setPrice(200.0);
        dish1.setCategory(category);

        Dish dish2 = new Dish();
        dish2.setId(2L);
        dish2.setName("Veg Biryani");
        dish2.setDescription("Spicy biryani with vegetables");
        dish2.setPrice(150.0);
        dish2.setCategory(category);

        List<Dish> expectedDishes = Arrays.asList(dish1, dish2);

        when(dishRepository.findByCategory(category)).thenReturn(expectedDishes);

        // When
        List<Dish> result = dishService.getDishesByCategory(category);
        System.out.println(result.get(0).getName());
        System.out.println(result.get(1).getName());

        // Then
        assertEquals(2, result.size());
        assertEquals("Paneer Butter Masala", result.get(0).getName());
        assertEquals("Veg Biryani", result.get(1).getName());
        verify(dishRepository, times(1)).findByCategory(category);
    }
}
