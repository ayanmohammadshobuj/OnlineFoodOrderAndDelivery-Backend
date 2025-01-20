package com.shobuj.controller;

import com.shobuj.entity.Restaurant;
import com.shobuj.entity.RestaurantContactInformation;
import com.shobuj.entity.User;
import com.shobuj.service.RestaurantContactInformationService;
import com.shobuj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants/contact")
public class RestaurantContactInformationController {

    @Autowired
    private RestaurantContactInformationService restaurantContactInformationService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<RestaurantContactInformation> createRestaurantContactInformation(@RequestBody RestaurantContactInformation req,
                                                                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = user.getRestaurant();
        RestaurantContactInformation restaurantContactInformation = restaurantContactInformationService.createRestaurantContactInformation(req, restaurant);
        return new ResponseEntity<>(restaurantContactInformation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantContactInformation> updateRestaurantContactInformation(@PathVariable Long id,
                                                                                           @RequestBody RestaurantContactInformation req) throws Exception {
        RestaurantContactInformation updatedContactInfo = restaurantContactInformationService.updateRestaurantContactInformation(id, req);
        return new ResponseEntity<>(updatedContactInfo, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RestaurantContactInformation> updateRestaurantContactInformationByJwt(@RequestBody RestaurantContactInformation req,
                                                                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = user.getRestaurant();
        RestaurantContactInformation updatedContactInfo = restaurantContactInformationService.updateRestaurantContactInformationByRestaurantId(restaurant.getId(), req);
        return new ResponseEntity<>(updatedContactInfo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurantContactInformation(@PathVariable Long id) throws Exception {
        restaurantContactInformationService.deleteRestaurantContactInformation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantContactInformation> getRestaurantContactInformationByRestaurantId(@PathVariable Long restaurantId) throws Exception {
        RestaurantContactInformation restaurantContactInformation = restaurantContactInformationService.getRestaurantContactInformationByRestaurantId(restaurantId);
        return new ResponseEntity<>(restaurantContactInformation, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<RestaurantContactInformation> getRestaurantContactInformationByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = user.getRestaurant();
        RestaurantContactInformation restaurantContactInformation = restaurantContactInformationService.getRestaurantContactInformationByRestaurantId(restaurant.getId());
        return new ResponseEntity<>(restaurantContactInformation, HttpStatus.OK);
    }
}