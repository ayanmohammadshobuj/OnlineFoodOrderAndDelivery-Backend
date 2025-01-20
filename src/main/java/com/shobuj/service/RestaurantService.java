package com.shobuj.service;

import com.shobuj.dto.RestaurantDto;
import com.shobuj.entity.Restaurant;
import com.shobuj.entity.User;
import com.shobuj.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;

    public void removeFromFavorites(Long restaurantId, User user) throws Exception;

    Restaurant updateRestaurantByJwt(CreateRestaurantRequest req, User user);

    List<Restaurant> getRestaurantByCategory(Long id);

    List<Restaurant> getRestaurantByLocation(String city);

    List<Restaurant> getRestaurantByCategoryAndLocation(Long id, String city);

    public void autoUpdateRestaurantTotalRating(Long restaurantId);

    void updateRestaurantStatusByTime();

    public Restaurant findById(Long id);

    Restaurant getRestaurantByFoodId(Long id);

    public List<Restaurant> searchRestaurantByAnything(String keyword);
}
