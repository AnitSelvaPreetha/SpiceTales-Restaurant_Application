package org.techm.capstone.restaurantManagementSystem.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User customer;

	@ManyToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<OrderItem> items;

	private double totalPrice;
	private LocalDateTime orderTime;

	private int tableNumber;
	
	@OneToOne(mappedBy="order", cascade = CascadeType.ALL)
	private Feedback feedback;
	
	public double getTotalPrice(){
		return totalPrice;
	}
	
	public User getUser() {
		return customer;
	}
	public double calculateTotalPrice() {
		return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
	}
	
	public String getFormattedOrderTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		return this.orderTime.format(formatter);
	}

// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> selectedDishes) {
		this.items = selectedDishes;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
}
