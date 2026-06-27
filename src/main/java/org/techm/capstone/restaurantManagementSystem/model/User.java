package org.techm.capstone.restaurantManagementSystem.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users") // avoid conflict with reserved keyword "user"
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Username is required")
	@Size(min = 3, max = 20, message = "Username must be 3–20 characters")
	private String username;

	@NotBlank(message = "Password is required")
	@Pattern(
			regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&]).{6,}$",
			message = "Password must be at least 6 characters and contain a letter, number, and special character"
			)
			
	private String password;

	/*@NotBlank(message = "Role is required")*/
	private String role;
	
	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String phone;


    @Transient
    @Column(nullable = false)
    private LocalDate dob;

	@OneToMany(mappedBy="user")
	private List<Reservation> reservations;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}