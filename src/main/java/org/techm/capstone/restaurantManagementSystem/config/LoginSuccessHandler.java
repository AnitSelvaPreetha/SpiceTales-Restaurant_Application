package org.techm.capstone.restaurantManagementSystem.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.techm.capstone.restaurantManagementSystem.model.User;

import java.io.IOException;

@Component





public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String role = authentication.getAuthorities().iterator().next().getAuthority();

		if (role.equals("ROLE_ADMIN")) {
			response.sendRedirect("/admin/menu");
		} else if (role.equals("ROLE_CUSTOMER")) {
			response.sendRedirect("/customer/menu");
		} else if (role.equals("ROLE_STAFF")) {
			response.sendRedirect("staff/menu");
		} else {
			response.sendRedirect("/home");
		}
	}
}
