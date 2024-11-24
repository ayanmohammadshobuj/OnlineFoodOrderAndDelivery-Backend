package com.shobuj.service;

import com.shobuj.entity.Category;
import com.shobuj.entity.Food;
import com.shobuj.entity.Restaurant;
import com.shobuj.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantFood(Long restaurantId, boolean isVegitarian, boolean isNonveg, boolean isSeasonal, String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailibility(Long foodId) throws Exception;
}
