package com.shobuj.service;

import com.shobuj.entity.Restaurant;
import com.shobuj.entity.RestaurantAddress;

public interface RestaurantAddressService {

    public RestaurantAddress createRestaurantAddress(RestaurantAddress restaurantAddress, Restaurant restaurant) throws Exception;
    public RestaurantAddress updateRestaurantAddress(Long restaurantAddressId, RestaurantAddress restaurantAddress) throws Exception;
    public void deleteRestaurantAddress(Long restaurantAddressId) throws Exception;
    public RestaurantAddress getRestaurantAddressByRestaurantId(Long restaurantId) throws Exception;

    RestaurantAddress updateRestaurantAddressByRestaurantId(Long id, RestaurantAddress req);
}
