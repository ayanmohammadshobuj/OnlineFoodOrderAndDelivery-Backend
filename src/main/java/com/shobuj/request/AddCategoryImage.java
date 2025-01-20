package com.shobuj.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCategoryImage {
    private byte[] image;
}
