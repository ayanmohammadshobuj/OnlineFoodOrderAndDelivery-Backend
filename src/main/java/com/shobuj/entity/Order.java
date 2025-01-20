package com.shobuj.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shobuj.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Date createdAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private List<OrderItem> orderItems;

    private int totalItem;
    private double totalPrice;
    private String paymentMethod;
    private String paymentStatus;

    // Customer shipping address and phone number
    private String apartment; // house, etc
    private String street;
    private String city;
    private String landmark; // building, etc
    private String label; // home, office, etc
    private String phone; // phone number

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    private Delivery delivery;

}
