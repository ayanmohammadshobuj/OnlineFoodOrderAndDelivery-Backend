package com.shobuj.service.impl;

import com.shobuj.entity.*;
import com.shobuj.enums.OrderStatus;
import com.shobuj.repository.OrderItemRepository;
import com.shobuj.repository.OrderRepository;
import com.shobuj.repository.UserRepository;
import com.shobuj.request.OrderRequest;
import com.shobuj.request.OrderStatusRequest;
import com.shobuj.service.CartService;
import com.shobuj.service.OrderService;
import com.shobuj.service.RestaurantService;
import com.shobuj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest orderRequest, String jwt) throws Exception {
        Restaurant restaurant = restaurantService.findById(orderRequest.getRestaurantId());
        User user = userService.findUserByJwtToken(jwt);

        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCustomer(user);
        order.setRestaurant(restaurant);
        order.setCreatedAt(new Date());
        order.setCity(orderRequest.getCity());
        order.setStreet(orderRequest.getStreet());
        order.setApartment(orderRequest.getApartment());
        order.setLabel(orderRequest.getLabel());
        order.setLandmark(orderRequest.getLandmark());
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setPhone(orderRequest.getPhone());
        order.setPaymentStatus(orderRequest.getPaymentStatus());

        Cart cart = cartService.findCartByUserId(user.getId());
        order.setRestaurant(cart.getRestaurant());
        order.setTotalItem(cart.getCartItems().size());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        double totalPrice = cartService.calculateCartTotals(cart);
        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        restaurant.getOrders().add(savedOrder);
        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
    }

    @Override
    public Order updateOrderStatus(Long orderId, String status) throws Exception {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
        order.setOrderStatus(OrderStatus.valueOf(status));
        return orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(OrderRequest orderRequest, Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
        order.setCity(orderRequest.getCity());
        order.setStreet(orderRequest.getStreet());
        order.setApartment(orderRequest.getApartment());
        order.setLabel(orderRequest.getLabel());
        order.setLandmark(orderRequest.getLandmark());
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setPhone(orderRequest.getPhone());
        order.setPaymentStatus(orderRequest.getPaymentStatus());
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() throws Exception {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByRestaurant(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        return orderRepository.findByRestaurant(restaurant);
    }

    @Override
    public List<Order> getOrdersByUser(User user) throws Exception {
        return orderRepository.findByCustomer(user);
    }

    @Override
    public Order updateOrderStatusByAdmin(Long orderId, OrderStatusRequest orderStatusRequest, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
        order.setOrderStatus(OrderStatus.valueOf(orderStatusRequest.getOrderStatus()));
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderStatusByDeliveryMan(Long orderId, String status, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        if (!user.getRole().equals("RIDERS")) {
            throw new Exception("You are not authorized to perform this action");
        }
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
        order.setOrderStatus(OrderStatus.valueOf(status));
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderStatusByRestaurant(Long orderId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
        if (!order.getRestaurant().getId().equals(restaurant.getId())) {
            throw new Exception("You are not authorized to perform this action");
        }

        // if order status is PENDING then make it CONFIRMED
        if (order.getOrderStatus().equals(OrderStatus.PENDING)) {
            order.setOrderStatus(OrderStatus.valueOf("CONFIRMED"));
            return orderRepository.save(order);
        }

        // if order status is CONFIRMED then make it PREPARING
        if (order.getOrderStatus().equals(OrderStatus.CONFIRMED)) {
            order.setOrderStatus(OrderStatus.valueOf("CONFIRMED"));
            return orderRepository.save(order);
        }

        // if order status is PREPARING then make it READY_FOR_PICKUP
        if (order.getOrderStatus().equals(OrderStatus.PREPARING)) {
            order.setOrderStatus(OrderStatus.valueOf("READY_FOR_PICKUP"));
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public Order updateOrderStatusByUser(Long orderId, String status, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
        if (!order.getCustomer().getId().equals(user.getId())) {
            throw new Exception("You are not authorized to perform this action");
        }
        order.setOrderStatus(OrderStatus.valueOf(status));
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findOrdersByStatus(String status) throws Exception {
        return orderRepository.findByOrderStatus(OrderStatus.valueOf(status));
    }
}