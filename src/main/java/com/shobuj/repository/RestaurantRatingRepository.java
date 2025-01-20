package com.shobuj.repository;

import com.shobuj.entity.RestaurantRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRatingRepository extends JpaRepository<RestaurantRating, Long> {
    List<RestaurantRating> findByRestaurantId(Long restaurantId);
}