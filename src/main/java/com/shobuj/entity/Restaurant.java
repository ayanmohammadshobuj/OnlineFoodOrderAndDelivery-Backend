package com.shobuj.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shobuj.assets.entity.RestaurantImages;
import com.shobuj.enums.Rating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String cuisineType;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "restaurant")
    private List<Category> categories;

    @Column(length = 1000)
    private String description;

    @JsonIgnore
    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private RestaurantAddress restaurantAddress;

    @JsonIgnore
    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private RestaurantContactInformation restaurantContactInformation;

    private String openingHours;
    private String closingHours;

    private boolean open = true;

    private double totalRating = 0.0;

    private LocalDateTime registrationDate;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "owner_id")
    private User user;



    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Food> foods;

    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private RestaurantImages restaurantImages;

//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "restaurant")
//    private List<IngredientCategory> ingredientCategories;
//
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "restaurant")
//    private List<IngredientsItem> ingredientsItems;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantRating> ratings;
}
