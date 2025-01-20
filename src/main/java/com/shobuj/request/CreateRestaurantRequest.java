package com.shobuj.request;

import com.shobuj.entity.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateRestaurantRequest {

    private Long id;
    private String name;
    private String cuisineType;

    // optional
    private List<Category> category;

    private String description;

    //optional
    private RestaurantAddress restaurantAddress;
    //optional
    private RestaurantContactInformation restaurantContactInformation;

    private String openingHours;
    private String closingHours;
    // Automatically set to the current date and time
    private boolean open = true;
    private LocalDateTime registrationDate;

    //optional
    private User user;

    //optional
    private List<Food> foods;

}
