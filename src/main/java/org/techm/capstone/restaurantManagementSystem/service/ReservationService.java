package org.techm.capstone.restaurantManagementSystem.service;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.techm.capstone.restaurantManagementSystem.model.RESERVATION_STATUS;
import org.techm.capstone.restaurantManagementSystem.model.Reservation;
import org.techm.capstone.restaurantManagementSystem.model.User;
import org.techm.capstone.restaurantManagementSystem.repository.ReservationRepository;
import org.techm.capstone.restaurantManagementSystem.repository.UserRepository;

@Service
public class ReservationService {
	
	@Autowired
    private  ReservationRepository reservationRepository;
	
	@Autowired
	private  UserRepository userRepo;
	
	public List<Reservation> fetchReservations() {
		return reservationRepository.findAll();
	}
	
	public List<Reservation> fetchReservationsByUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String username = auth.getName();
    	
    	User user = userRepo.findByUsername(username).get();
    	
    	return reservationRepository.findAllByUserId(user.getId());
	}

    
    
   // public void saveReservation(LocalDate checkIn, int NoOfGuests) {
   public void saveReservation(LocalDateTime checkIn, int noOfGuests){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String username = auth.getName();
    	
    	User user = userRepo.findByUsername(username).get();
    	
    	/*BReservation res = new Reservation();
    	res.setUser(user);
    	res.setCheckIn(checkIn);
    	res.setNoOfGuests(NoOfGuests);
    	res.setCreatedAt(LocalDate.now());
    	res.setStatus(RESERVATION_STATUS.PENDING);  */
       int hour = checkIn.getHour();
       if (hour < 8 || hour >= 22) {
           throw new IllegalArgumentException("Reservations must be between 8:00 AM and 10:00 PM.");
       }

       Reservation res = new Reservation();
       res.setUser(user);
       res.setCheckIn(checkIn); // now LocalDateTime
       res.setNoOfGuests(noOfGuests);
       res.setCreatedAt(LocalDateTime.now()); // now LocalDateTime
       res.setStatus(RESERVATION_STATUS.PENDING);
    	
    	reservationRepository.save(res);
    	
    }
    
    public void approveReservation(Long reservationId, int tableNumber) {
    	Reservation res = reservationRepository.findById(reservationId).get();

        LocalDateTime checkIn = res.getCheckIn();

        // Check for conflicting reservation
        boolean isBooked = reservationRepository.isTableBooked(tableNumber, checkIn, RESERVATION_STATUS.CONFIRMED);
        if (isBooked) {
            throw new IllegalArgumentException("Table " + tableNumber + " is already booked at " + checkIn);
        }

        res.setTableNumber(tableNumber);
    	res.setStatus(RESERVATION_STATUS.CONFIRMED);
    	reservationRepository.save(res);
    }
    
    public void cancelReservation(Long reservationId) {
    	Reservation res = reservationRepository.findById(reservationId).get();
    	res.setStatus(RESERVATION_STATUS.CANCELLED);
    	res.setTableNumber(-1);
    	reservationRepository.save(res);
    }

}