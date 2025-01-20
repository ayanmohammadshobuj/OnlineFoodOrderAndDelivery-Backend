package com.shobuj.service.impl;

import com.shobuj.dto.RestaurantDto;
import com.shobuj.entity.*;
import com.shobuj.repository.*;
import com.shobuj.request.CreateRestaurantRequest;
import com.shobuj.response.BadRequestException;
import com.shobuj.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantAddressRepository restaurantAddressRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantContactInformationRepository restaurantContactInformationRepository;

//    @Autowired
//    private CategoryRepository categoryRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

//        Category category = categoryRepository.save(req.getCategory());
//        RestaurantAddress restaurantAddress = restaurantAddressRepository.save(req.getRestaurantAddress());
//        RestaurantContactInformation restaurantContactInformation = restaurantContactInformationRepository.save(req.getRestaurantContactInformation());

        Restaurant restaurant = new Restaurant();
        restaurant.setName(req.getName());
        restaurant.setCuisineType(req.getCuisineType());

//        restaurant.setCategories(List.of(category));
        // or which one is correct?
//        restaurant.setCategories(req.getCategory());

        restaurant.setDescription(req.getDescription());

//        restaurant.setRestaurantAddress(restaurantAddress);
//        restaurant.setRestaurantContactInformation(restaurantContactInformation);

        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setClosingHours(req.getClosingHours());
        restaurant.setOpen(true);
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setUser(user);
//        restaurant.setFoods(req.getFoods());


        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        // Rating Update

        if (restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if (restaurant.getDescription()!=null){
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if (restaurant.getName()!=null){
            restaurant.setName(updatedRestaurant.getName());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(id);
        if (opt.isEmpty()){
            throw new Exception("Restaurant not found with id " + id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByUserId(userId);
        if (restaurant == null){
            throw new Exception("Restaurant not found with id " + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setTitle(restaurant.getName());
        dto.setCuisine(restaurant.getCuisineType());
        dto.setCategory("Category");
        dto.setStatus(restaurant.isOpen() ? "Open" : "Closed");
        dto.setDpImage(restaurant.getRestaurantImages().getDpImage());
//        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);


        // Not Working
//        if (user.getFavourites().contains(dto)){
//            user.getFavourites().remove(dto);
//        }
//        else user.getFavourites().add(dto);

        boolean isFavourited = false;
        List<RestaurantDto> favourites = user.getFavourites();
        for (RestaurantDto favourite : favourites) {
            if (favourite.getId().equals(restaurantId)){
                isFavourited = true;
                break;
            }
        }
        if (isFavourited) {
            favourites.removeIf(favourite -> favourite.getId().equals(restaurantId));
        } else {
            favourites.add(dto);
        }

        userRepository.save(user);
        return dto;
    }

    @Override
    public void removeFromFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        List<RestaurantDto> favourites = user.getFavourites();
        favourites.removeIf(favourite -> favourite.getId().equals(restaurantId));
        userRepository.save(user);
    }

    @Override
    public Restaurant updateRestaurantByJwt(CreateRestaurantRequest req, User user) {
        Restaurant restaurant = user.getRestaurant();
        restaurant.setName(req.getName());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setClosingHours(req.getClosingHours());
        return restaurantRepository.save(restaurant);
    }

    // New
    @Override
    public List<Restaurant> getRestaurantByCategory(Long id) {
        return null;
    }

    // New
    @Override
    public List<Restaurant> getRestaurantByLocation(String city) {
        return null;
    }

    // New
    @Override
    public List<Restaurant> getRestaurantByCategoryAndLocation(Long id, String city) {
        return null;
    }

    @Override
    public void autoUpdateRestaurantTotalRating(Long restaurantId) {

    }

    @Override
    public void updateRestaurantStatusByTime() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Restaurant restaurant : restaurants) {
            LocalTime openingTime = LocalTime.parse(restaurant.getOpeningHours(), formatter);
            LocalTime closingTime = LocalTime.parse(restaurant.getClosingHours(), formatter);
            LocalTime now = LocalTime.now();

            if (openingTime.isBefore(now) && closingTime.isAfter(now)) {
                restaurant.setOpen(true);
            } else {
                restaurant.setOpen(false);
            }
            restaurantRepository.save(restaurant);
        }
    }

    @Override
    public Restaurant findById(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        if (restaurant.isEmpty()) {
            throw new BadRequestException("Can not Find Restaurant With Id " + id);
        }

        return restaurant.get();
    }

    @Override
    public Restaurant getRestaurantByFoodId(Long id) {
        return restaurantRepository.findByFoodsId(id);
    }

    @Override
    public List<Restaurant> searchRestaurantByAnything(String keyword) {return restaurantRepository.findBySearchQueryInRestaurantAndFood(keyword);
    }


    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }

}
