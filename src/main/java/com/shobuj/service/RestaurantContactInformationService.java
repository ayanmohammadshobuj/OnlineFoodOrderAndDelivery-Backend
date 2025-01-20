package com.shobuj.service;

import com.shobuj.entity.Restaurant;
import com.shobuj.entity.RestaurantContactInformation;

public interface RestaurantContactInformationService {
    public RestaurantContactInformation createRestaurantContactInformation(RestaurantContactInformation restaurantContactInformation, Restaurant restaurant) throws Exception;
    public RestaurantContactInformation updateRestaurantContactInformation(Long restaurantContactInformationId, RestaurantContactInformation RestaurantContactInformation) throws Exception;
    public void deleteRestaurantContactInformation(Long restaurantContactInformationId) throws Exception;
    public RestaurantContactInformation getRestaurantContactInformationByRestaurantId(Long restaurantId) throws Exception;

    RestaurantContactInformation updateRestaurantContactInformationByRestaurantId(Long id, RestaurantContactInformation req);
}
