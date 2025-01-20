package com.shobuj.assets.repository;

import com.shobuj.assets.entity.RestaurantImages;
import com.shobuj.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantImagesRepository extends JpaRepository<RestaurantImages, Integer> {
    RestaurantImages findByRestaurant(Restaurant restaurant);
}