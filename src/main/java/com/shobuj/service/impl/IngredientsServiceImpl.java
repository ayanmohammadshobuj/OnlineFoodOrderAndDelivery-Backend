package com.shobuj.service.impl;

import com.shobuj.entity.IngredientCategory;
import com.shobuj.entity.IngredientsItem;
import com.shobuj.entity.Restaurant;
import com.shobuj.repository.IngredientCategoryRepository;
import com.shobuj.repository.IngredientItemRepository;
import com.shobuj.service.IngredientsService;
import com.shobuj.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImpl implements IngredientsService {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory ingredientCategory = new IngredientCategory();

        ingredientCategory.setRestaurant(restaurant);
        ingredientCategory.setName(name);

        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> optional = ingredientCategoryRepository.findById(id);

        if (optional.isEmpty()) {
            throw new Exception("Ingredient Category not found");
        }

        return optional.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientCategoryById(categoryId);

        IngredientsItem ingredientsItem = new IngredientsItem();
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setCategory(category);

        IngredientsItem ingredient = ingredientItemRepository.save(ingredientsItem);
        category.getIngredients().add(ingredient);

        return ingredient;
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) throws Exception {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem = ingredientItemRepository.findById(id);
        if (optionalIngredientsItem.isEmpty()) {
            throw new Exception("Ingredient Item not found");
        }
        IngredientsItem ingredientsItem = optionalIngredientsItem.get();

        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
        return ingredientItemRepository.save(ingredientsItem);
    }
}

