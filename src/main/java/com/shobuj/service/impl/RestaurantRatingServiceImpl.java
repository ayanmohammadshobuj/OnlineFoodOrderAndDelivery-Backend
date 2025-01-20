package com.shobuj.service.impl;

import com.shobuj.entity.Restaurant;
import com.shobuj.entity.RestaurantRating;
import com.shobuj.enums.Rating;
import com.shobuj.repository.RestaurantRatingRepository;
import com.shobuj.repository.RestaurantRepository;
import com.shobuj.service.RestaurantRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantRatingServiceImpl implements RestaurantRatingService {

    @Autowired
    private RestaurantRatingRepository restaurantRatingRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public RestaurantRating rateRestaurant(Long restaurantId, Rating rating) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new Exception("Restaurant not found"));

        RestaurantRating restaurantRating = new RestaurantRating();
        restaurantRating.setRating(rating);
        restaurantRating.setRestaurant(restaurant);
        restaurantRating = restaurantRatingRepository.save(restaurantRating);

        autoUpdateRestaurantTotalRating(restaurantId);

        return restaurantRating;
    }

    @Override
    public void deleteRating(Long restaurantId, Long ratingId) throws Exception {
        restaurantRatingRepository.deleteById(ratingId);
        autoUpdateRestaurantTotalRating(restaurantId);
    }

    @Override
    public void autoUpdateRestaurantTotalRating(Long restaurantId) {
        List<RestaurantRating> ratings = restaurantRatingRepository.findByRestaurantId(restaurantId);
        double totalRating = ratings.stream().mapToDouble(r -> r.getRating().getValue()).sum();
        double averageRating = totalRating / ratings.size();

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
        restaurant.setTotalRating(averageRating);
        restaurantRepository.save(restaurant);
    }
}