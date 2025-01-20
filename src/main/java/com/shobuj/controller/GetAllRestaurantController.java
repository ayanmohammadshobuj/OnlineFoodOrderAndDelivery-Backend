package com.shobuj.controller;

import com.shobuj.entity.Restaurant;
import com.shobuj.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurant() throws Exception {
        List<Restaurant> restaurant = restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    // Getting All Restaurants by Category
    @GetMapping("/restaurants/category/{id}")
    public ResponseEntity<List<Restaurant>> getRestaurantByCategory(@PathVariable("id") Long id) throws Exception {
        List<Restaurant> restaurant = restaurantService.getRestaurantByCategory(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    // Getting All Restaurants by Location
    @GetMapping("/restaurants/location/{city}")
    public ResponseEntity<List<Restaurant>> getRestaurantByLocation(@PathVariable("city") String city) throws Exception {
        List<Restaurant> restaurant = restaurantService.getRestaurantByLocation(city);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
