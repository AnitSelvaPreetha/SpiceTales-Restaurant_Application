package org.techm.capstone.restaurantManagementSystem.model;

import java.util.Base64;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;

@Entity
public class Dish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	private double price;
	
	@Lob
    private byte[] image;
	
	//@ManyToMany(mappedBy = "dishes")
	//private List<Order> orders;
	
	
	@Transient
	private int quantity = 1;



    @Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DishCategory category;

    public static void addAttribute(String dish, Dish updatedDish) {
    }

    public DishCategory getCategory() {
		return category;
	}
	public void setCategory(DishCategory category) {
		this.category = category;
	}
	
	public enum DishCategory {
	    INDIAN,
	    CHINESE,
	    STARTER,
	    MAIN_COURSE,
	    DESSERT,
	    JUICE,
	    CHAT_ITEMS
	}

// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public Dish() {
	}
	

	public Dish(String name, String description, double price, DishCategory category, byte[] image) {
		
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.image = image;
		
	}

	

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public String getImageBase64() {
        if (image==null||image.length==0){
            return null;
        }
        return Base64.getEncoder().encodeToString(this.image);

	}




}