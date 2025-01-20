package com.shobuj.repository;

import com.shobuj.entity.RestaurantContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantContactInformationRepository extends JpaRepository<RestaurantContactInformation, Long> {
    RestaurantContactInformation findByRestaurantId(Long restaurantId);
}
