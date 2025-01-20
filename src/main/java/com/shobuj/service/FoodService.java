package com.shobuj.service;

import com.shobuj.entity.Food;
import com.shobuj.request.CreateFoodRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodService {
    Food createFood(CreateFoodRequest req, MultipartFile image, Long restaurantId) throws Exception;

    void deleteFood(Long foodId) throws Exception;

    List<Food> searchFood(String keyword);

    Food findFoodById(Long foodId) throws Exception;

    Food updateAvailibility(Long foodId) throws Exception;

    List<Food> getAllFoodItems(Long id);

    Food updateFood(Long id, CreateFoodRequest req, MultipartFile image);

    List<Food> getAllFoodByCategory(Long id);
}