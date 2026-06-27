package org.techm.capstone.restaurantManagementSystem.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {

    

	public OrderItem() {
		super();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dishName;
    private double price;
    private int quantity;



	@ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    public OrderItem(Long id, String dishName, double price, int quantity, Order order) {
		
		this.id = id;
		this.dishName = dishName;
		this.price = price;
		this.quantity = quantity;
		this.order = order;


	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

// Getters and Setters
}