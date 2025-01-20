package com.shobuj.controller;

import com.shobuj.entity.Category;
import com.shobuj.entity.User;
import com.shobuj.service.CategoryService;
import com.shobuj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/admin/category", consumes = {"multipart/form-data"})
    public ResponseEntity<Category> createCategory(@RequestPart("name") String name,
                                                   @RequestPart("image") MultipartFile image,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Category createdCategory = categoryService.createCategory(name, user.getId(), image);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Category> categories = categoryService.findCategoryByRestaurantId(user.getId());
        return new ResponseEntity<>(categories, HttpStatus.CREATED);
    }

    // Get All Categories
    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @DeleteMapping("/admin/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) throws Exception {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/admin/category/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id,
                                                  @RequestPart("name") String name,
                                                  @RequestPart("image") MultipartFile image) throws Exception {
        Category updatedCategory = categoryService.updateCategory(id, name, image);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

}