package com.shobuj.service.impl;

import com.shobuj.entity.Category;
import com.shobuj.entity.Food;
import com.shobuj.entity.Restaurant;
import com.shobuj.request.CreateFoodRequest;
import com.shobuj.repository.FoodRepository;
import com.shobuj.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {

        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImage(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredients());
        food.setSeasonal(req.isSeasional());
        food.setVegetarian(req.isVegetarian());

        Food saveFood = foodRepository.save(food);
        restaurant.getFoods().add(saveFood);

        return saveFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.delete(food);
    }

    @Override
    public List<Food> getRestaurantFood(Long restaurantId, boolean isVegitarian, boolean isNonveg, boolean isSeasonal, String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if (isVegitarian) {
            foods = filterByVegetarian(foods,isVegitarian);
        }
        if (isNonveg) {
            foods = filterByNonveg(foods,isNonveg);
        }
        if (isSeasonal) {
            foods = filterBySeasonal(foods,isSeasonal);
        }
        if (foodCategory != null && !foodCategory.equals("")) {
            foods = filterByCategory(foods,foodCategory);

        }

        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory()!=null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> food.isVegetarian()==false).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegitarian) {

        return foods.stream().filter(food -> food.isVegetarian()==isVegitarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {

        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);

        if (optionalFood.isEmpty()){
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
}
