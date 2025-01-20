//package com.shobuj.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class ShippingAddress {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String apartment; // house, etc
//    private String street;
//    private String city;
//    private String landmark; // building, etc
//    private String label; // home, office, etc
//    private String phone; // phone number
//
//    @JsonIgnore
//    @OneToOne(mappedBy = "shippingAddress", cascade = CascadeType.ALL)
//    private Order order;
//
//}
