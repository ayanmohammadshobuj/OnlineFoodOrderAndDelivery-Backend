//package com.shobuj.assets.controller;
//
//import com.shobuj.assets.entity.FoodImages;
//import com.shobuj.assets.repository.FoodImagesRepository;
//import com.shobuj.entity.Food;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("food/images")
//public class FoodImagesController {
//
//    @Autowired
//    private FoodImagesRepository foodImagesRepository;
//
//    @PostMapping(consumes = "multipart/form-data")
//    public ResponseEntity<FoodImages> createFoodImage(@RequestParam Food itemId, @RequestPart("file1") MultipartFile file1, @RequestPart("file2") MultipartFile file2) throws Exception {
//        FoodImages foodImages = FoodImages.builder().food(itemId).itemImage1(file1.getBytes()).itemImage2(file2.getBytes()).build();
//        foodImagesRepository.save(foodImages);
//        foodImages.setItemImage1(null);
//        foodImages.setItemImage2(null);
//        return ResponseEntity.ok(foodImages);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<FoodImages>> getAllFoodImage() {
//        List<FoodImages> foodImagesList = foodImagesRepository.findAll();
//        return ResponseEntity.ok(foodImagesList);
//    }
//
//
//
//}
