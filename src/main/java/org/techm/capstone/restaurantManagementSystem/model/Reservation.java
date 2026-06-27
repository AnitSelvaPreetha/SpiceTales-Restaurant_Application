package org.techm.capstone.restaurantManagementSystem.model;

//import java.time.LocalDate;
import java.time.LocalDateTime;



import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User user;
	
	//private LocalDate checkIn;

	//private LocalDate createdAt;
    private LocalDateTime checkIn;
    private LocalDateTime createdAt;
	
	private int tableNumber;
	
	private int noOfGuests;
	
	@Enumerated(EnumType.STRING)
	private RESERVATION_STATUS status;
	
	

	public RESERVATION_STATUS getStatus() {
		return status;
	}

	public void setStatus(RESERVATION_STATUS status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//	public LocalDate getCheckIn() {
//		return checkIn;
//	}
//
//	public void setCheckIn(LocalDate checkIn) {
//		this.checkIn = checkIn;
//	}
//
//	public LocalDate getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(LocalDate createdAt) {
//		this.createdAt = createdAt;
//	}



    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public int getNoOfGuests() {
		return noOfGuests;
	}

	public void setNoOfGuests(int noOfGuests) {
		this.noOfGuests = noOfGuests;
	}
	
	

}
