package org.techm.capstone.restaurantManagementSystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.techm.capstone.restaurantManagementSystem.model.RESERVATION_STATUS;
import org.techm.capstone.restaurantManagementSystem.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	List<Reservation> findAllByUserId(Long userId);
    //boolean existsByTableNumberAndCheckInAndStatus(int tableNumber, LocalDateTime checkIn, RESERVATION_STATUS status);
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reservation r WHERE r.tableNumber = :tableNumber AND r.checkIn = :checkIn AND r.status = :status")
    boolean isTableBooked(@Param("tableNumber") int tableNumber, @Param("checkIn") LocalDateTime checkIn, @Param("status") RESERVATION_STATUS status);
}
