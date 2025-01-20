package com.shobuj.controller;

import com.shobuj.entity.Category;
import com.shobuj.entity.Restaurant;
import com.shobuj.service.CategoryService;
import com.shobuj.service.UserService;
import com.shobuj.service.impl.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantServiceImpl restaurantServiceImpl;

    @GetMapping("/categrory/{id}")
    public ResponseEntity<List<Category>> getRestaurantCategory(@PathVariable("id") Long id) throws Exception {
        List<Category> categories = categoryService.findCategoryByRestaurantId(id);
        return new ResponseEntity<>(categories, HttpStatus.CREATED);
    }

    // Getting All Restaurants by Category and Location
    @GetMapping("/restaurants/category/{id}/location/{city}")
    public ResponseEntity<List<Restaurant>> getRestaurantByCategoryAndLocation(@PathVariable("id") Long id, @PathVariable("city") String city) throws Exception {
        List<Restaurant> restaurant = restaurantServiceImpl.getRestaurantByCategoryAndLocation(id, city);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    // Getting All Categories
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() throws Exception {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
