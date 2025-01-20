package com.shobuj.controller;

import com.shobuj.entity.Restaurant;
import com.shobuj.entity.RestaurantAddress;
import com.shobuj.entity.User;
import com.shobuj.service.RestaurantAddressService;
import com.shobuj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants/address")
public class RestaurantAddressController {

    @Autowired
    private RestaurantAddressService restaurantAddressService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<RestaurantAddress> createRestaurantAddress(@RequestBody RestaurantAddress req,
                                                                     @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = user.getRestaurant();
        RestaurantAddress restaurantAddress = restaurantAddressService.createRestaurantAddress(req, restaurant);
        return new ResponseEntity<>(restaurantAddress, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantAddress> updateRestaurantAddress(@PathVariable Long id,
                                                                     @RequestBody RestaurantAddress req) throws Exception {
        RestaurantAddress updatedAddress = restaurantAddressService.updateRestaurantAddress(id, req);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @PutMapping("/jwt")
    public ResponseEntity<RestaurantAddress> updateRestaurantAddressByJwt(@RequestBody RestaurantAddress req,
                                                                     @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = user.getRestaurant();
        RestaurantAddress updatedAddress = restaurantAddressService.updateRestaurantAddressByRestaurantId(restaurant.getId(), req);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurantAddress(@PathVariable Long id) throws Exception {
        restaurantAddressService.deleteRestaurantAddress(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantAddress> getRestaurantAddressByRestaurantId(@PathVariable Long restaurantId) throws Exception {
        RestaurantAddress restaurantAddress = restaurantAddressService.getRestaurantAddressByRestaurantId(restaurantId);
        return new ResponseEntity<>(restaurantAddress, HttpStatus.OK);
    }

    @GetMapping("/jwt")
    public ResponseEntity<RestaurantAddress> getRestaurantAddressByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = user.getRestaurant();
        RestaurantAddress restaurantAddress = restaurantAddressService.getRestaurantAddressByRestaurantId(restaurant.getId());
        return new ResponseEntity<>(restaurantAddress, HttpStatus.OK);
    }
}