package com.shobuj.controller;

import com.shobuj.dto.RestaurantDto;
import com.shobuj.entity.*;
import com.shobuj.repository.CartItemRepository;
import com.shobuj.repository.CartRepository;
import com.shobuj.repository.UserRepository;
import com.shobuj.service.RestaurantService;
import com.shobuj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/open/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestParam String keyword) throws Exception {

        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant() throws Exception {

        List<Restaurant> restaurant = restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@PathVariable Long id) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favourites")
    public ResponseEntity<RestaurantDto> addToFavourites(@RequestHeader("Authorization") String jwt,
                                                      @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        RestaurantDto restaurant = restaurantService.addToFavorites(id, user);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/remove-favourites")
    public ResponseEntity<Void> removeFromFavourites(@RequestHeader("Authorization") String jwt,
                                                     @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        restaurantService.removeFromFavorites(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Getting All Food Of This Restaurant
    @GetMapping("/foods")
    public ResponseEntity<List<Food>> getRestaurantFoods(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantService.getRestaurantByUserId(user.getId()).getId());
        List<Food> foods = restaurant.getFoods();
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    // Getting Restaurant By foodId (foodId)
    @GetMapping("/food")
    public ResponseEntity<Restaurant> getRestaurantByFoodId(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByUserId(user.getId());
        List<CartItem> cartItem = cartItemRepository.findByCartId(cart.getId());
        Food food = cartItem.get(0).getFood();
        Restaurant restaurant = restaurantService.getRestaurantByFoodId(food.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    // Get Favorites Restaurants Of User
    @GetMapping("/favorites")
    public ResponseEntity<List<RestaurantDto>> getFavorites(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<RestaurantDto> favorites = user.getFavourites();
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

    // Get Favorites Restaurant By Restaurant Id
    @GetMapping("/favorites/{id}")
    public ResponseEntity<RestaurantDto> getFavoritesById(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        RestaurantDto favorites = user.getFavourites().stream().filter(f -> f.getId().equals(id)).findFirst().orElse(null);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

    @GetMapping("/search/anything")
    public ResponseEntity<List<Restaurant>> searchRestaurantByAnything(@RequestParam String keyword) throws Exception {
        List<Restaurant> restaurants = restaurantService.searchRestaurantByAnything(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

}
