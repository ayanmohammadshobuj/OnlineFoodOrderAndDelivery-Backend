package com.shobuj.repository;

import com.shobuj.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {

    public Cart findByUserId(Long userId);
}
