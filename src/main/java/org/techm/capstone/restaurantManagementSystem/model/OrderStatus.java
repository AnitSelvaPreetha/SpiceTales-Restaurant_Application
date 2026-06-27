package org.techm.capstone.restaurantManagementSystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String dishName;
    private int quantity;
    private int tableNumber;
    private String username;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Status {
        NEW,
        IN_PROGRESS,
        READY_TO_SERVE,
        COMPLETE
    }

    public OrderStatus() {}

    public OrderStatus(Long orderId, String dishName, int quantity, int tableNumber, String username) {
        this.orderId = orderId;
        this.dishName = dishName;
        this.quantity = quantity;
        this.tableNumber = tableNumber;
        this.username = username;
        this.status = Status.NEW;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
