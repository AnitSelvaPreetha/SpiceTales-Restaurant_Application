package org.techm.capstone.restaurantManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.techm.capstone.restaurantManagementSystem.model.Dish;
import org.techm.capstone.restaurantManagementSystem.model.Dish.DishCategory;
@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findByCategory(DishCategory category);

    @Query("select d from Dish d where " +
            "lower(d.name) like lower(concat('%', :q, '%')) or " +
            "lower(d.description) like lower(concat('%', :q, '%'))")
    List<Dish> searchByNameOrDescription(@Param("q") String q);


}
