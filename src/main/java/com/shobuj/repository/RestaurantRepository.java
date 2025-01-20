package com.shobuj.repository;

import com.shobuj.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%',:query, '%')) " + "OR lower(r.cuisineType) LIKE lower(concat('%', :query, '%'))")
    List<Restaurant> findBySearchQuery(String query);

    // Seach by city name of the restaurantAddress
    @Query("SELECT r FROM Restaurant r WHERE lower(r.restaurantAddress.city) LIKE lower(concat('%',:city, '%'))")
    List<Restaurant> findByCity(String city);

    // search restaurants by anything in the restaurant or its food items
    @Query("SELECT r FROM Restaurant r " +
            "LEFT JOIN r.foods f " +
            "LEFT JOIN r.restaurantAddress ra " +
            "LEFT JOIN r.categories c " +
            "WHERE lower(r.name) LIKE lower(concat('%', :query, '%')) " +
            "OR lower(r.cuisineType) LIKE lower(concat('%', :query, '%')) " +
            "OR lower(r.description) LIKE lower(concat('%', :query, '%')) " +
            "OR lower(f.name) LIKE lower(concat('%', :query, '%')) " +
            "OR lower(f.description) LIKE lower(concat('%', :query, '%')) " +
            "OR lower(ra.city) LIKE lower(concat('%', :query, '%')) " +
            "OR lower(ra.street) LIKE lower(concat('%', :query, '%')) " +
            "OR lower(ra.country) LIKE lower(concat('%', :query, '%')) " +
            "OR lower(c.name) LIKE lower(concat('%', :query, '%'))")
    List<Restaurant> findBySearchQueryInRestaurantAndFood(String query);
    Restaurant findByUserId(Long userId);


    Restaurant findByFoodsId(Long id);

    Restaurant findByOrdersId(Long id);
}
