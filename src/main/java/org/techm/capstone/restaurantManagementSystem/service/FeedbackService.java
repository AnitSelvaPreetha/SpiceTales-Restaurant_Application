package org.techm.capstone.restaurantManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.techm.capstone.restaurantManagementSystem.model.Feedback;
import org.techm.capstone.restaurantManagementSystem.model.Order;
import org.techm.capstone.restaurantManagementSystem.model.User;
import org.techm.capstone.restaurantManagementSystem.repository.FeedbackRepository;
import org.techm.capstone.restaurantManagementSystem.repository.OrderRepository;
import org.techm.capstone.restaurantManagementSystem.repository.UserRepository;

@Service
public class FeedbackService {
	
	@Autowired
	private FeedbackRepository fbrepo;
	
	@Autowired
	private OrderRepository orderrepo;
	
	@Autowired
	private UserRepository userrepo;
	
	public void saveFeedback(Long id, int rating, String comment) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		User user = userrepo.findByUsername(username).get();
		
		Order order = orderrepo.findById(id).get();
		
		Feedback fb = new Feedback();
		fb.setOrder(order);
		fb.setRating(rating);
		fb.setComment(comment);
		fb.setUser(user);

		
		fbrepo.save(fb);
		
	}

}
