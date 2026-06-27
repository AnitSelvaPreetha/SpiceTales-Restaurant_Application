package org.techm.capstone.restaurantManagementSystem.controller;
 
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.techm.capstone.restaurantManagementSystem.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;

/*
 * @Controller
 * 
 * @RequestMapping("/reservations") public class ReservationController { private
 * final ReservationService reservationService;
 * 
 * public ReservationController(ReservationService reservationService) {
 * this.reservationService = reservationService; }
 * 
 * @GetMapping public String GetAllReservationsByUser(Model model){
 * model.addAttribute("reservations",
 * reservationService.fetchReservationsByUser()); return
 * "customer/reservations"; }
 * 
 * @GetMapping("/new") public String showReservationForm() { return
 * "customer/reservation-form"; }
 * 
 * 
 * @PostMapping("/book") public String addReservation(
 * 
 * @RequestParam(value="checkIn")LocalDate checkIn,
 * 
 * @RequestParam(value="noOfGuests")int noOfGuests, RedirectAttributes ra) {
 * reservationService.saveReservation(checkIn, noOfGuests);
 * ra.addFlashAttribute("booked", "Table booking Request placed successfully");
 * return "redirect:/customer/menu"; }
 * 
 * @PostMapping("/approve") public String approveReservation(
 * 
 * @RequestParam(value="reservationId")Long reservationId,
 * 
 * @RequestParam(value="tableNumber")int tableNumber) {
 * reservationService.approveReservation(reservationId, tableNumber); return
 * "redirect:/admin/reservations"; }
 * 
 * @GetMapping("/cancel/{id}") public String approveReservation(
 * 
 * @PathVariable Long id) { reservationService.cancelReservation(id); return
 * "redirect:/admin/reservations"; } }
 */





@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String GetAllReservationsByUser(Model model) {
        model.addAttribute("reservations", reservationService.fetchReservationsByUser());
        return "customer/reservations";
    }

    @GetMapping("/new")
    public String showReservationForm() {
        return "customer/reservation-form";
    }

    @PostMapping("/book")
    public String addReservation(
            @RequestParam("checkIn")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkIn,

            @RequestParam("noOfGuests") int noOfGuests,
            RedirectAttributes ra) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime maxDateTime = now.plusDays(30);


        try {
            reservationService.saveReservation(checkIn, noOfGuests);
            ra.addFlashAttribute("booked");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/reservations/new";
        }


        // Validate check-in datetime
        if (checkIn.isBefore(now) || checkIn.isAfter(maxDateTime)) {
            ra.addFlashAttribute("error", "Invalid check-in time. It must be within the next 30 days.");
            return "redirect:/reservations/new";
        }

        // Validate number of guests
        if (noOfGuests < 1 || noOfGuests > 10) {
            ra.addFlashAttribute("error", "Invalid number of guests. It must be between 1 and 10.");
            return "redirect:/reservations/new";
        }

//        reservationService.saveReservation(checkIn, noOfGuests);
//        ra.addFlashAttribute("booked", "Table booking request placed successfully");
        return "redirect:/reservations";
    }

    @PostMapping("/approve")
    public String approveReservation(
        @RequestParam(value="reservationId") Long reservationId,
        @RequestParam(value="tableNumber") int tableNumber, RedirectAttributes ra) {

        try {
            reservationService.approveReservation(reservationId, tableNumber);
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("conflictError", ex.getMessage());
            return "redirect:/admin/reservations";
        }


        return "redirect:/admin/reservations";
    }




    @GetMapping("/cancel/{id}")
    public String approveReservation(
        @PathVariable Long id) {
        reservationService.cancelReservation(id);
        return "redirect:/admin/reservations";
    }
}