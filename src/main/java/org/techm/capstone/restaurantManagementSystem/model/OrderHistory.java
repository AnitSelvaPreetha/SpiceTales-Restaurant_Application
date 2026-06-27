package org.techm.capstone.restaurantManagementSystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_history")
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        // auto-generated, do NOT copy from OrderStatus

    private Long orderId;
    private String dishName;
    private int quantity;
    private int tableNumber;
    private String username;

    @Enumerated(EnumType.STRING)
    private OrderStatus.Status status;

    private LocalDateTime completedAt;

    public OrderHistory() {}

    public OrderHistory(Long orderId, String dishName, int quantity, int tableNumber, String username, OrderStatus.Status status, LocalDateTime completedAt) {
        this.orderId = orderId;
        this.dishName = dishName;
        this.quantity = quantity;
        this.tableNumber = tableNumber;
        this.username = username;
        this.status = status;
        this.completedAt = completedAt;
    }

    // Getters and setters (no setter for id unless absolutely required)
    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public OrderStatus.Status getStatus() { return status; }
    public void setStatus(OrderStatus.Status status) { this.status = status; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}
