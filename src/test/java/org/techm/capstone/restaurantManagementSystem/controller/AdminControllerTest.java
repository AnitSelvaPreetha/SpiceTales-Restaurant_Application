package org.techm.capstone.restaurantManagementSystem.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.techm.capstone.restaurantManagementSystem.config.SecurityConfigTest;
import org.techm.capstone.restaurantManagementSystem.repository.UserRepository;
import org.techm.capstone.restaurantManagementSystem.service.DishService;
import org.techm.capstone.restaurantManagementSystem.service.OrderService;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfigTest.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DishService dishService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderService orderService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testAdminMenu() throws Exception {
        mockMvc.perform(get("/admin/menu"))
               .andExpect(status().isOk())
               .andExpect(view().name("admin/menu"))
               .andExpect(model().attributeExists("dishes", "dish"));
    }

//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void testAddDish() throws Exception {
//        mockMvc.perform(post("/admin/addDish")
//                .param("name", "Biryani")
//                .param("description", "Spicy")
//                .param("price", "200")
//                .param("category", "INDIAN"))
//               .andExpect(status().is3xxRedirection())
//               .andExpect(redirectedUrl("/admin/menu"));
//    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testViewAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(List.of());
        mockMvc.perform(get("/admin/orders"))
               .andExpect(status().isOk())
               .andExpect(view().name("admin/orders"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteDish() throws Exception {
        mockMvc.perform(post("/admin/menu/delete/1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/admin/menu"));
    }
}
