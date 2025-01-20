package com.shobuj.controller;

import com.shobuj.entity.Food;
import com.shobuj.entity.User;
import com.shobuj.service.FoodService;
import com.shobuj.service.RestaurantService;
import com.shobuj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anyone/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(name);
        return new ResponseEntity<>(foods, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> findFoodById(@PathVariable Long id) throws Exception {
        Food food = foodService.findFoodById(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    //Get All Food Items of a Restaurant by Restaurant ID
    @GetMapping("/all/{id}")
    public ResponseEntity<List<Food>> getAllFoodItems(@PathVariable Long id) throws Exception {
        List<Food> foods = foodService.getAllFoodItems(id);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    // Get All Food By Category ID
    @GetMapping("/category/{id}")
    public ResponseEntity<List<Food>> getAllFoodByCategory(@PathVariable Long id) throws Exception {
        List<Food> foods = foodService.getAllFoodByCategory(id);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }



}
