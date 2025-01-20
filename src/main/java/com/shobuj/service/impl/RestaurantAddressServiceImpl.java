// src/main/java/com/shobuj/service/impl/RestaurantAddressServiceImpl.java
package com.shobuj.service.impl;

import com.shobuj.entity.Restaurant;
import com.shobuj.entity.RestaurantAddress;
import com.shobuj.repository.RestaurantAddressRepository;
import com.shobuj.repository.RestaurantRepository;
import com.shobuj.service.RestaurantAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantAddressServiceImpl implements RestaurantAddressService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantAddressRepository restaurantAddressRepository;

    @Override
    public RestaurantAddress createRestaurantAddress(RestaurantAddress req, Restaurant restaurant) throws Exception {
        // Find the restaurant by the user ID of the logged-in user
        Restaurant foundRestaurant = restaurantRepository.findByUserId(restaurant.getUser().getId());

        // Create a new RestaurantAddress object
        RestaurantAddress restaurantAddress = new RestaurantAddress();
        restaurantAddress.setRestaurant(foundRestaurant);
        restaurantAddress.setFullAddress(req.getFullAddress());
        restaurantAddress.setStreet(req.getStreet());
        restaurantAddress.setCity(req.getCity());
        restaurantAddress.setThana(req.getThana());
        restaurantAddress.setPostalCode(req.getPostalCode());
        restaurantAddress.setCountry(req.getCountry());

        // Save and return the created RestaurantAddress
        return restaurantAddressRepository.save(restaurantAddress);
    }

    @Override
    public RestaurantAddress updateRestaurantAddress(Long restaurantAddressId, RestaurantAddress restaurantAddress) throws Exception {
        Optional<RestaurantAddress> existingAddressOpt = restaurantAddressRepository.findById(restaurantAddressId);
        if (existingAddressOpt.isEmpty()) {
            throw new Exception("RestaurantAddress not found with id " + restaurantAddressId);
        }
        RestaurantAddress existingAddress = existingAddressOpt.get();
        existingAddress.setFullAddress(restaurantAddress.getFullAddress());
        existingAddress.setStreet(restaurantAddress.getStreet());
        existingAddress.setCity(restaurantAddress.getCity());
        existingAddress.setThana(restaurantAddress.getThana());
        existingAddress.setPostalCode(restaurantAddress.getPostalCode());
        existingAddress.setCountry(restaurantAddress.getCountry());
        return restaurantAddressRepository.save(existingAddress);
    }

    @Override
    public void deleteRestaurantAddress(Long restaurantAddressId) throws Exception {
        if (!restaurantAddressRepository.existsById(restaurantAddressId)) {
            throw new Exception("RestaurantAddress not found with id " + restaurantAddressId);
        }
        restaurantAddressRepository.deleteById(restaurantAddressId);
    }

    @Override
    public RestaurantAddress getRestaurantAddressByRestaurantId(Long restaurantId) throws Exception {
        RestaurantAddress restaurantAddress = restaurantAddressRepository.findByRestaurantId(restaurantId);
        if (restaurantAddress == null) {
            throw new Exception("RestaurantAddress not found for restaurant id " + restaurantId);
        }
        return restaurantAddress;
    }

    @Override
    public RestaurantAddress updateRestaurantAddressByRestaurantId(Long id, RestaurantAddress req) {
        RestaurantAddress restaurantAddress = restaurantAddressRepository.findByRestaurantId(id);
        if (restaurantAddress == null) {
            return null;
        }
        restaurantAddress.setFullAddress(req.getFullAddress());
        restaurantAddress.setStreet(req.getStreet());
        restaurantAddress.setCity(req.getCity());
        restaurantAddress.setThana(req.getThana());
        restaurantAddress.setPostalCode(req.getPostalCode());
        restaurantAddress.setCountry(req.getCountry());
        return restaurantAddressRepository.save(restaurantAddress);
    }
}