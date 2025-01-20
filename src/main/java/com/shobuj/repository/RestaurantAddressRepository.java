package com.shobuj.repository;

import com.shobuj.entity.RestaurantAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantAddressRepository extends JpaRepository<RestaurantAddress, Long> {
    RestaurantAddress findByRestaurantId(Long restaurantId);
}
