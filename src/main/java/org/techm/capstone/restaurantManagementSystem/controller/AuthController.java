package org.techm.capstone.restaurantManagementSystem.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.techm.capstone.restaurantManagementSystem.model.User;
import org.techm.capstone.restaurantManagementSystem.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;
	

	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") @Valid User user,
	BindingResult result,
	Model model,
	RedirectAttributes redirectAttributes) {

	if (userService.existsByUsername(user.getUsername())) {
	result.rejectValue("username", null, "Username already registered");
	}

//	if (result.hasErrors()) {
//	return "redirect:/register";
//	}

        if (result.hasErrors()) {
            model.addAttribute("error","User already exists!!"); // optional, usually already present
            return "register"; // NOT redirect
        }
//        if (user.getDob() != null) {
//            int age = Period.between(user.getDob(), LocalDate.now()).getYears();
//            if (age < 16) {
//                result.rejectValue("dob", null, "You must be at least 16 years old to register.");
//                return "register";
//            }
//        }



        userService.register(user);

	// Set success message
	redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");

	return "redirect:/login";
	}
	


	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        RedirectAttributes ra) {

        userService.authenticate(username, password); // throws exception if invalid

        ra.addFlashAttribute("message", "Login successful!");
        return "redirect:/login";
    }

}
