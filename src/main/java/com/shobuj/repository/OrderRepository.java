package com.shobuj.repository;

import com.shobuj.entity.Order;
import com.shobuj.entity.Restaurant;
import com.shobuj.entity.User;
import com.shobuj.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByRestaurant(Restaurant restaurant);

    List<Order> findByCustomer(User user);

    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
