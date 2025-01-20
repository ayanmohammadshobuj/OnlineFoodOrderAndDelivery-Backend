package com.shobuj.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shobuj.entity.Category;
import com.shobuj.entity.Restaurant;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private long price;
    private List<String> images;
    private Restaurant restaurant;
    private Category category;

    public static CreateFoodRequest fromJson(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, CreateFoodRequest.class);
    }

    public Long getId() {
        return null;
    }
}