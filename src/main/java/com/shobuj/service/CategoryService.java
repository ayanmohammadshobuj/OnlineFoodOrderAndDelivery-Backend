package com.shobuj.service;

import com.shobuj.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    Category createCategory(String name, Long userId, MultipartFile image) throws Exception;

    List<Category> findCategoryByRestaurantId(Long id) throws Exception;

    Category findCategoryById(Long id) throws Exception;

    List<Category> getAllCategories();

    void deleteCategory(Long id);

    Category updateCategory(Long id, String name, MultipartFile image);
}