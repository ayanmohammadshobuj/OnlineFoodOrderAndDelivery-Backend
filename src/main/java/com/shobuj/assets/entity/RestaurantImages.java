package com.shobuj.assets.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shobuj.entity.Restaurant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantImages {



//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(length = 1000000)
    private byte[] dpImage;

    @Lob
    @Column(length = 1000000)
    private byte[] coverImage;

    @Lob
    @Column(length = 1000000)
    private byte[] displayImage;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}