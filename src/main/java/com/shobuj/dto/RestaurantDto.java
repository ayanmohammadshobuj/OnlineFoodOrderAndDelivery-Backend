package com.shobuj.dto;



import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;


@Data
@Embeddable
public class RestaurantDto {
    private Long id;
    private String title;
    private String category;
    private String cuisine;
    private String status;
    private String description;

    @Column(length = 10000000)
    private byte[] dpImage;
}
