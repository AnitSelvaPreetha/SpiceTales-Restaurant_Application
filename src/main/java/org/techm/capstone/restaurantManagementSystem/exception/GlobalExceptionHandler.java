package org.techm.capstone.restaurantManagementSystem.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserExists(UserAlreadyExistsException ex, Model model) {
        model.addAttribute("error", "User already registered!!");
        model.addAttribute("user", new org.techm.capstone.restaurantManagementSystem.model.User());
        return "register"; // show register form with error message
    }

    @ExceptionHandler(InvalidLoginException.class)
    public String handleInvalidLogin(InvalidLoginException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", ex.getMessage());
        return "redirect:/login"; // redirect back to login page
    }

    // Handle validation exceptions thrown outside controllers (optional)
//    @ExceptionHandler(BindException.class)
//    public String handleBindException(BindException ex, Model model) {
//        for (FieldError fe : ex.getFieldErrors()) {
//            model.addAttribute(fe.getField() + "Error", fe.getDefaultMessage());
//        }
//        model.addAttribute("user", new org.techm.capstone.restaurantManagementSystem.model.User());
//        return "register";
//    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, RedirectAttributes ra) {
        ra.addFlashAttribute("error", "An unexpected error occurred.");
        return "redirect:/";
    }
}