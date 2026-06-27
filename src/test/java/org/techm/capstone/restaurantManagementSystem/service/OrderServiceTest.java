package org.techm.capstone.restaurantManagementSystem.service;

//package org.techm.capstone.restaurantManagementSystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.techm.capstone.restaurantManagementSystem.model.*;
import org.techm.capstone.restaurantManagementSystem.repository.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DishService dishService;

    private User testUser;
    private Dish testDish;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("john");

        testDish = new Dish();
        testDish.setId(1L);
        testDish.setName("Pizza");
        testDish.setPrice(10.0);

        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setCustomer(testUser);
        testOrder.setOrderTime(LocalDateTime.now());
    }

//    @Test
//    void testPlaceOrder() {
//        List<Long> dishIds = Arrays.asList(1L, 1L);
//        List<Integer> quantities = Arrays.asList(1, 2);
//
//        when(userRepository.findByUsername("john")).thenReturn(Optional.of(testUser));
//        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
//            Order order = invocation.getArgument(0);
//            order.setId(1L);
//            return order;
//        });
//        when(dishService.getDishById(1L)).thenReturn(testDish);
//
//        Order result = orderService.placeOrder(dishIds, quantities, "john");
//
//       assertNotNull(result);
//     assertEquals(1L, result.getCustomer().getId());
//       assertEquals(1, result.getItems().size());
//        assertEquals(3, result.getItems().get(0).getQuantity());
//       assertEquals("Pizza", result.getItems().get(0).getDishName());
//        verify(orderRepository, times(2)).save(any(Order.class));
//    }

    @Test
    void testGetLatestOrderForCustomer() {
        when(orderRepository.findTopByCustomerIdOrderByOrderTimeDesc(1L)).thenReturn(testOrder);

        Order result = orderService.getLatestOrderForCustomer(1L);
        assertNotNull(result);
        assertEquals(testUser, result.getCustomer());
    }

    @Test
    void testGetOrdersForUser() {
        OrderItem item = new OrderItem();
        item.setPrice(20.0);
        testOrder.setItems(List.of(item));

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(testUser));
        when(orderRepository.findByCustomer(testUser)).thenReturn(List.of(testOrder));

        List<Order> result = orderService.getOrdersForUser("john");

        assertEquals(1, result.size());
        assertEquals(20.0, result.get(0).getTotalPrice());
    }

    @Test
    void testGetOrdersForUser_UserNotFound() {
        when(userRepository.findByUsername("invalid")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrdersForUser("invalid");
        });

        assertTrue(exception.getMessage().contains("User not found"));
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(List.of(testOrder));

        List<Order> result = orderService.getAllOrders();

        assertEquals(1, result.size());
        assertEquals(testOrder, result.get(0));
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        Order result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(2L);
        });

        assertEquals("order not found", exception.getMessage());
    }
}

