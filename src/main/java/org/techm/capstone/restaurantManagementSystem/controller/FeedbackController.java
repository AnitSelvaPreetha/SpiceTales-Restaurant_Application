package org.techm.capstone.restaurantManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.techm.capstone.restaurantManagementSystem.service.FeedbackService;

import jakarta.servlet.http.HttpSession;


  @Controller
  
  @RequestMapping("/feedback") public class FeedbackController {
  
  @Autowired private FeedbackService fbService;
  
  
  @PostMapping("/{id}")
  public String saveFeedback(@PathVariable Long id, 
                             @RequestParam(value="rating", required=false) int rating,
                             @RequestParam(value="comment", required=false) String comment,
                             RedirectAttributes redirectAttributes) {
      
      fbService.saveFeedback(id, rating, comment);
      redirectAttributes.addFlashAttribute("feedbackSubmitted_" + id, true); // Store feedback state
      return "redirect:/customer/profile";
  }
  }
 
 