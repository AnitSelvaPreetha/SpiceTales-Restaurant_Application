package org.techm.capstone.restaurantManagementSystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.csrf().disable()
	.headers().frameOptions().disable()
	.and()
	.authorizeHttpRequests(auth -> auth
	.requestMatchers("/", "/home/**", "/splash/**", "/welcome", "/register", "/login", "/css/**", "/img/**").permitAll()
	.requestMatchers("/admin/**").hasRole("ADMIN")
	.requestMatchers("/customer/**").hasRole("CUSTOMER")
	.requestMatchers("/staff/**").hasRole("STAFF")
	.anyRequest().authenticated()
	)
	.formLogin(form -> form
	.loginPage("/login")
	.successHandler(loginSuccessHandler)
	.permitAll()
	)
	.logout(logout -> logout
	.logoutUrl("/logout")
	.logoutSuccessUrl("/login?logout=true")
	.invalidateHttpSession(true)
	.deleteCookies("JSESSIONID")
	.permitAll()
	);

	return http.build();
	}


	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(customUserDetailsService)
				.passwordEncoder(passwordEncoder()).and().build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}