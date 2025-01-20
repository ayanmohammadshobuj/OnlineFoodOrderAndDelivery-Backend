package com.shobuj.assets.controller;

import com.shobuj.assets.entity.RestaurantImages;
import com.shobuj.assets.repository.RestaurantImagesRepository;
import com.shobuj.entity.Restaurant;
import com.shobuj.entity.User;
import com.shobuj.repository.RestaurantRepository;
import com.shobuj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("restaurant/images")
public class RestaurantImagesController {

    @Autowired
    private RestaurantImagesRepository restaurantImagesRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<RestaurantImages> createRestaurantImage(@RequestPart("dpImage") MultipartFile dpImage,
                                                                  @RequestPart("coverImage") MultipartFile coverImage,
                                                                  @RequestPart("displayImage") MultipartFile displayImage) throws Exception {
        // Extract the user information from the authentication context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the user in the database
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }

        // Get the restaurant associated with the user
        Restaurant restaurant = user.getRestaurant();
        if (restaurant == null) {
            throw new Exception("Restaurant not found for the user");
        }

        // Create and save the RestaurantImages entity
        RestaurantImages restaurantImages = RestaurantImages.builder()
                .restaurant(restaurant)
                .dpImage(dpImage.getBytes())
                .coverImage(coverImage.getBytes())
                .displayImage(displayImage.getBytes())
                .build();

        restaurantImagesRepository.save(restaurantImages);
        restaurantImages.setDpImage(null);
        restaurantImages.setCoverImage(null);
        restaurantImages.setDisplayImage(null);

        return ResponseEntity.ok(restaurantImages);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantImages>> getAllRestaurantImages() {
        List<RestaurantImages> restaurantImagesList = restaurantImagesRepository.findAll();
        return ResponseEntity.ok(restaurantImagesList);
    }

    @PutMapping(consumes = "multipart/form-data")
    public ResponseEntity<RestaurantImages> updateRestaurantImage(@RequestPart("dpImage") MultipartFile dpImage,
                                                                  @RequestPart("coverImage") MultipartFile coverImage,
                                                                  @RequestPart("displayImage") MultipartFile displayImage) throws Exception {
        // Extract the user information from the authentication context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the user in the database
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }

        // Get the restaurant associated with the user
        Restaurant restaurant = user.getRestaurant();
        if (restaurant == null) {
            throw new Exception("Restaurant not found for the user");
        }

        // Find the existing RestaurantImages entity
        RestaurantImages restaurantImages = restaurantImagesRepository.findByRestaurant(restaurant);
        if (restaurantImages == null) {
            throw new Exception("Restaurant images not found");
        }

        // Update and save the RestaurantImages entity
        restaurantImages.setDpImage(dpImage.getBytes());
        restaurantImages.setCoverImage(coverImage.getBytes());
        restaurantImages.setDisplayImage(displayImage.getBytes());
        restaurantImagesRepository.save(restaurantImages);
        restaurantImages.setDpImage(null);
        restaurantImages.setCoverImage(null);
        restaurantImages.setDisplayImage(null);

        return ResponseEntity.ok(restaurantImages);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantImages> getRestaurantImageByRestaurantId(@PathVariable Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new Exception("Restaurant not found"));

        RestaurantImages restaurantImages = restaurantImagesRepository.findByRestaurant(restaurant);
        if (restaurantImages == null) {
            throw new Exception("Restaurant images not found");
        }

        restaurantImages.setDpImage(restaurantImages.getDpImage());
        restaurantImages.setCoverImage(restaurantImages.getCoverImage());
        restaurantImages.setDisplayImage(restaurantImages.getDisplayImage());

        return ResponseEntity.ok(restaurantImages);
    }

    @GetMapping("/jwt")
    public ResponseEntity<RestaurantImages> getRestaurantImageByJwt() throws Exception {
        // Extract the user information from the JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the user in the database
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }

        // Get the restaurant associated with the user
        Restaurant restaurant = user.getRestaurant();
        if (restaurant == null) {
            throw new Exception("Restaurant not found for the user");
        }

        RestaurantImages restaurantImages = restaurantImagesRepository.findByRestaurant(restaurant);
        if (restaurantImages == null) {
            throw new Exception("Restaurant images not found");
        }

        restaurantImages.setDpImage(restaurantImages.getDpImage());
        restaurantImages.setCoverImage(restaurantImages.getCoverImage());
        restaurantImages.setDisplayImage(restaurantImages.getDisplayImage());

        return ResponseEntity.ok(restaurantImages);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRestaurantImage() throws Exception {
        // Extract the user information from the JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the user in the database
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }

        // Get the restaurant associated with the user
        Restaurant restaurant = user.getRestaurant();
        if (restaurant == null) {
            throw new Exception("Restaurant not found for the user");
        }

        RestaurantImages restaurantImages = restaurantImagesRepository.findByRestaurant(restaurant);
        if (restaurantImages == null) {
            throw new Exception("Restaurant images not found");
        }

        restaurantImagesRepository.delete(restaurantImages);

        return ResponseEntity.ok("Restaurant images deleted successfully");
    }
}