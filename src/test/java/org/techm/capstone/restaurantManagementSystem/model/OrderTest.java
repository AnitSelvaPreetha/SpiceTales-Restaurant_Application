package org.techm.capstone.restaurantManagementSystem.model;

//package org.techm.capstone.restaurantManagementSystem.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void calculateTotalPrice_ShouldReturnCorrectSum() {
        OrderItem item1 = new OrderItem();
        item1.setPrice(100.0);
        item1.setQuantity(2); // 200

        OrderItem item2 = new OrderItem();
        item2.setPrice(50.0);
        item2.setQuantity(3); // 150

        Order order = new Order();
        order.setItems(Arrays.asList(item1, item2));

        double total = order.calculateTotalPrice();
        assertEquals(350.0, total, 0.01);
    }

    @Test
    void getFormattedOrderTime_ShouldReturnFormattedDateTime() {
        Order order = new Order();
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 25, 18, 45);
        order.setOrderTime(dateTime);

        String formatted = order.getFormattedOrderTime();
        assertEquals("25-12-2024 18:45", formatted);
    }
}

