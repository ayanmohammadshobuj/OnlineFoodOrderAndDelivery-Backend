package com.shobuj.service;

import com.shobuj.entity.RestaurantRating;
import com.shobuj.enums.Rating;

public interface RestaurantRatingService {
    RestaurantRating rateRestaurant(Long restaurantId, Rating rating) throws Exception;
    void deleteRating(Long restaurantId, Long ratingId) throws Exception;
    void autoUpdateRestaurantTotalRating(Long restaurantId);
}