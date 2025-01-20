package com.shobuj.service.impl;

import com.shobuj.entity.Category;
import com.shobuj.entity.Food;
import com.shobuj.entity.Restaurant;
import com.shobuj.repository.CategoryRepository;
import com.shobuj.repository.RestaurantRepository;
import com.shobuj.request.CreateFoodRequest;
import com.shobuj.repository.FoodRepository;
import com.shobuj.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Food createFood(CreateFoodRequest req, MultipartFile image, Long restaurantId) throws Exception {
        // Check if the category exists
        Optional<Category> categoryOptional = categoryRepository.findById(req.getCategory().getId());
        if (categoryOptional.isEmpty()) {
            throw new Exception("Category not found.");
        }

        // Check if the restaurant exists
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isEmpty()) {
            throw new Exception("Restaurant not found.");
        }

        Food food = new Food();
        food.setFoodCategory(categoryOptional.get());
        food.setDescription(req.getDescription());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setAvailable(true); // Set default availability to true
        food.setCreationDate(LocalDateTime.now());
        food.setRestaurant(restaurantOptional.get());

        if (image != null && !image.isEmpty()) {
            try {
                food.setImage(image.getBytes());
            } catch (IOException e) {
                throw new Exception("Failed to store image", e);
            }
        }

        return foodRepository.save(food);
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.delete(food);
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (optionalFood.isEmpty()) {
            throw new Exception("Food not exist.");
        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailibility(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }

    @Override
    public List<Food> getAllFoodItems(Long id) {
        return foodRepository.findAllByRestaurantId(id);
    }

    @Override
    public Food updateFood(Long id, CreateFoodRequest req, MultipartFile image) {
        Food food = foodRepository.findById(id).get();
        food.setName(req.getName());
        food.setDescription(req.getDescription());
        food.setPrice(req.getPrice());
        food.setFoodCategory(categoryRepository.findById(req.getCategory().getId()).get());

        if (image != null && !image.isEmpty()) {
            try {
                food.setImage(image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return foodRepository.save(food);
    }

    @Override
    public List<Food> getAllFoodByCategory(Long id) {
        return foodRepository.findAllByFoodCategoryId(id);
    }


}