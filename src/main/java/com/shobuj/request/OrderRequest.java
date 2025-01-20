package com.shobuj.request;

import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private String paymentMethod;
    private String paymentStatus;
    private String apartment; // house, etc
    private String street;
    private String city;
    private String landmark; // building, etc
    private String label; // home, office, etc
    private String phone; // phone number
}
