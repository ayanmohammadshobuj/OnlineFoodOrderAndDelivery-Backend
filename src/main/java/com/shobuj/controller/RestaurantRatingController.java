package com.shobuj.controller;

import com.shobuj.entity.RestaurantRating;
import com.shobuj.enums.Rating;
import com.shobuj.service.RestaurantRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant/rating")
public class RestaurantRatingController {

    @Autowired
    private RestaurantRatingService restaurantRatingService;

    @PostMapping("/{restaurantId}")
    public RestaurantRating rateRestaurant(@PathVariable Long restaurantId, @RequestBody RatingRequest ratingRequest) throws Exception {
        Rating ratingEnum = Rating.valueOf(ratingRequest.getRating().toUpperCase());
        return restaurantRatingService.rateRestaurant(restaurantId, ratingEnum);
    }

    @DeleteMapping("/{restaurantId}/{ratingId}")
    public void deleteRating(@PathVariable Long restaurantId, @PathVariable Long ratingId) throws Exception {
        restaurantRatingService.deleteRating(restaurantId, ratingId);
    }

    public static class RatingRequest {
        private String rating;

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }
    }
}