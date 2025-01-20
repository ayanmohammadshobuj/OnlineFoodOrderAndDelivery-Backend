package com.shobuj.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String cardName;
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    private double total;

    private String paymentStatus;

    private String paymentDate;
    private String paymentTime;

    private String transactionId;
    private String paymentMethod;


}
