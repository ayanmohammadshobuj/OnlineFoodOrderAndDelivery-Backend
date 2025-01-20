package com.shobuj.controller;

import com.shobuj.entity.Food;
import com.shobuj.entity.User;
import com.shobuj.request.CreateFoodRequest;
import com.shobuj.response.MessegeResponse;
import com.shobuj.service.FoodService;
import com.shobuj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Food> createFood(@RequestPart("food") String foodJson,
                                           @RequestPart("image") MultipartFile image,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        CreateFoodRequest req = CreateFoodRequest.fromJson(foodJson);
        Food food = foodService.createFood(req, image, user.getRestaurant().getId());
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessegeResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);
        MessegeResponse res = new MessegeResponse();
        res.setMessage("Food deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable Long id,
                                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailibility(id);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Food> updateFood(@PathVariable Long id,
                                           @RequestPart("food") String foodJson,
                                           @RequestPart(value = "image", required = false) MultipartFile image,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        CreateFoodRequest req = CreateFoodRequest.fromJson(foodJson);
        Food food = foodService.updateFood(id, req, image);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    // Get All Food Items of a Restaurant by Logged in Restaurant Owner
    @GetMapping("/all")
    public ResponseEntity<List<Food>> getAllFoodItems(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getAllFoodItems(user.getRestaurant().getId());
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

}