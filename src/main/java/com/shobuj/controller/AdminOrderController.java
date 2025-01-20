package com.shobuj.controller;

import com.shobuj.entity.Order;
import com.shobuj.entity.User;
import com.shobuj.request.OrderStatusRequest;
import com.shobuj.service.OrderService;
import com.shobuj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;


    @GetMapping("/order")
    public ResponseEntity<List<Order>> getRestaurantsAllOrders(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> order = orderService.getOrdersByRestaurant(jwt);
        return ResponseEntity.ok(order);
    }

    // Getting all orders
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> order = orderService.getAllOrders();
        return ResponseEntity.ok(order);
    }

    // Updating order status by admin
    @PutMapping("/order/{orderId}")
    public ResponseEntity<Order> updateOrderStatusByAdmin(
            @RequestHeader("Authorization") String jwt,
            @RequestBody OrderStatusRequest orderStatusRequest,
            @PathVariable Long orderId) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrderStatusByAdmin(orderId, orderStatusRequest, jwt);
        return ResponseEntity.ok(order);
    }

    // Updating order status by restaurant
    @PutMapping("/order/restaurant/{orderId}")
    public ResponseEntity<Order> updateOrderStatusByRestaurant(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrderStatusByRestaurant(orderId, jwt);
        return ResponseEntity.ok(order);
    }

    // Order Cancel by admin/restaurant
    @PutMapping("/order/cancel/{orderId}")
    public ResponseEntity<Order> cancelOrder(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(order);
    }



}
