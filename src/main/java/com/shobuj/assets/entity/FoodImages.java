//package com.shobuj.assets.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.shobuj.entity.Food;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class FoodImages {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int id;
//
////    private Long itemId;
//
////    @JsonIgnoreProperties("foodImages")
////    @OneToOne(mappedBy = "foodImages")
////    private Food food;
//
//    @Lob
//    @Column(length = 1000000)
//    private byte[] itemImage1;
//
//    @Lob
//    @Column(length = 1000000)
//    private byte[] itemImage2;
//
//}
//
//
