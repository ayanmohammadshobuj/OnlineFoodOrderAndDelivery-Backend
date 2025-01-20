package com.shobuj.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shobuj.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    // Customer Information
    private String customerName;

    @Column(length = 1000)
    private String customerAddress;

    private String landmark;
    private String customerPhone;


    // Restaurant Information
    private String restaurantName;

    @Column(length = 1000)
    private String restaurantAddress;

    private String restaurantPhone;


    private LocalDateTime deliveryAcceptedTime;
    private LocalDateTime deliveryCompletedTime;
    private int OTP;

    // Rider Information
    @ManyToOne
    @JoinColumn(name = "rider_id")
    private Rider rider;

    // Order Information
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
