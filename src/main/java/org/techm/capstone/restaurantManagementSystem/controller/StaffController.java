package org.techm.capstone.restaurantManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.techm.capstone.restaurantManagementSystem.model.Dish;
import org.techm.capstone.restaurantManagementSystem.model.OrderHistory;
import org.techm.capstone.restaurantManagementSystem.model.OrderStatus;
import org.techm.capstone.restaurantManagementSystem.repository.OrderHistoryRepository;
import org.techm.capstone.restaurantManagementSystem.service.OrderStatusService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;


//    @GetMapping("/menu")
//    public String adminMenu(Model model) {
//        return "staff/menu";
//    }

    @GetMapping("/menu")
    public String staffMenu(Model model) {
//        model.addAttribute("menuItems", menuService.findAll());          // optional
        model.addAttribute("statuses", orderStatusService.getAllFIFO()); // FIFO list
        return "staff/menu"; // templates/staff/menu.html (or staff-menu.html depending on filename)
    }


    @GetMapping("/history")
    public String viewHistory(Model model) {
        List<OrderHistory> history = orderHistoryRepository.findAllByOrderByCompletedAtDesc();
        model.addAttribute("history", history);
        return "staff/history"; // templates/staff/history.html
    }


    // View FIFO queue
    @GetMapping("/fifo")
    public String viewFIFO(Model model) {
        List<OrderStatus> statuses = orderStatusService.getAllFIFO();
        model.addAttribute("statuses", statuses);
        return "staff/fifo";
    }

    // Update status by id (button actions)
    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long id, @RequestParam OrderStatus.Status status) {
        orderStatusService.updateStatus(id, status);
        return "redirect:/staff/menu";
    }
}
