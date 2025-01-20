package com.shobuj.service.impl;

import com.shobuj.entity.Restaurant;
import com.shobuj.entity.RestaurantContactInformation;
import com.shobuj.repository.RestaurantContactInformationRepository;
import com.shobuj.repository.RestaurantRepository;
import com.shobuj.service.RestaurantContactInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantContactInformationServiceImpl implements RestaurantContactInformationService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantContactInformationRepository restaurantContactInformationRepository;

    @Override
    public RestaurantContactInformation createRestaurantContactInformation(RestaurantContactInformation req, Restaurant restaurant) throws Exception {
        // Find the restaurant by the user ID of the logged-in user
        Restaurant foundRestaurant = restaurantRepository.findByUserId(restaurant.getUser().getId());

        // Create a new RestaurantContactInformation object
        RestaurantContactInformation restaurantContactInformation = new RestaurantContactInformation();
        restaurantContactInformation.setRestaurant(foundRestaurant);
        restaurantContactInformation.setMobile(req.getMobile());
        restaurantContactInformation.setEmail(req.getEmail());
        restaurantContactInformation.setWebsite(req.getWebsite());

        // Save and return the created RestaurantContactInformation
        return restaurantContactInformationRepository.save(restaurantContactInformation);
    }

    @Override
    public RestaurantContactInformation updateRestaurantContactInformation(Long restaurantContactInformationId, RestaurantContactInformation req) throws Exception {
        Optional<RestaurantContactInformation> existingContactInfoOpt = restaurantContactInformationRepository.findById(restaurantContactInformationId);
        if (existingContactInfoOpt.isEmpty()) {
            throw new Exception("RestaurantContactInformation not found with id " + restaurantContactInformationId);
        }
        RestaurantContactInformation existingContactInfo = existingContactInfoOpt.get();
        existingContactInfo.setMobile(req.getMobile());
        existingContactInfo.setEmail(req.getEmail());
        existingContactInfo.setWebsite(req.getWebsite());
        return restaurantContactInformationRepository.save(existingContactInfo);
    }

    @Override
    public void deleteRestaurantContactInformation(Long restaurantContactInformationId) throws Exception {
        if (!restaurantContactInformationRepository.existsById(restaurantContactInformationId)) {
            throw new Exception("RestaurantContactInformation not found with id " + restaurantContactInformationId);
        }
        restaurantContactInformationRepository.deleteById(restaurantContactInformationId);
    }

    @Override
    public RestaurantContactInformation getRestaurantContactInformationByRestaurantId(Long restaurantId) throws Exception {
        RestaurantContactInformation restaurantContactInformation = restaurantContactInformationRepository.findByRestaurantId(restaurantId);
        if (restaurantContactInformation == null) {
            throw new Exception("RestaurantContactInformation not found for restaurant id " + restaurantId);
        }
        return restaurantContactInformation;
    }

    @Override
    public RestaurantContactInformation updateRestaurantContactInformationByRestaurantId(Long id, RestaurantContactInformation req) {
        RestaurantContactInformation restaurantContactInformation = restaurantContactInformationRepository.findByRestaurantId(id);
        if (restaurantContactInformation == null) {
            return null;
        }
        restaurantContactInformation.setMobile(req.getMobile());
        restaurantContactInformation.setEmail(req.getEmail());
        restaurantContactInformation.setWebsite(req.getWebsite());
        return restaurantContactInformationRepository.save(restaurantContactInformation);
    }
}