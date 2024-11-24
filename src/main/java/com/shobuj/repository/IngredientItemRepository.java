package com.shobuj.repository;

import com.shobuj.entity.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientItemRepository extends JpaRepository<IngredientsItem, Long> {

     List<IngredientsItem> findByRestaurantId(Long restaurantId);
}
