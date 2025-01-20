package com.shobuj.controller;

import com.shobuj.entity.Order;
import com.shobuj.entity.User;
import com.shobuj.enums.OrderStatus;
import com.shobuj.request.OrderRequest;
import com.shobuj.service.CartService;
import com.shobuj.service.OrderService;
import com.shobuj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(
            @RequestHeader("Authorization") String jwt,
            @RequestBody OrderRequest orderDTO) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(orderDTO, jwt);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.findOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/order/user/jwt")
    public ResponseEntity<List<Order>> getOrdersByUser(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> order = orderService.getOrdersByUser(user);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/order/{orderId}")
    public ResponseEntity<Order> updateOrder(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId,
            @RequestBody OrderRequest orderDTO) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrder(orderDTO, orderId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/order/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId,
            @RequestParam String status) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(order);
    }

    // update order status by delivery
    @PutMapping("/order/{orderId}/status/delivery")
    public ResponseEntity<Order> updateOrderStatusByDeliveryMan(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId,
            @RequestParam String status) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrderStatusByDeliveryMan(orderId, status, jwt);
        return ResponseEntity.ok(order);
    }

    // Get Orders by Order Status
    @GetMapping("/order/status")
    public ResponseEntity<List<Order>> findOrdersByStatus(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        // get all orders with status preparing and confirmed
        List<Order> orders = orderService.findOrdersByStatus(OrderStatus.CONFIRMED.toString());
        return ResponseEntity.ok(orders);
    }

}
