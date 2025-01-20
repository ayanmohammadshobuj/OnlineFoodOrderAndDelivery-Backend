package com.shobuj.service;


import com.shobuj.entity.Order;
import com.shobuj.entity.User;
import com.shobuj.request.OrderRequest;
import com.shobuj.request.OrderStatusRequest;

import java.util.List;

public interface OrderService {
        public Order createOrder(OrderRequest orderRequest, String jwt) throws Exception;

        public Order findOrderById(Long orderId) throws Exception;

        public Order updateOrderStatus(Long orderId, String status) throws Exception;

        public Order cancelOrder(Long orderId) throws Exception;

        public Order updateOrder(OrderRequest orderRequest, Long orderId) throws Exception;

        public List<Order> getAllOrders() throws Exception;

        public List<Order> getOrdersByRestaurant(String jwt) throws Exception;

        public List<Order> getOrdersByUser(User user) throws Exception;

        public Order updateOrderStatusByAdmin(Long orderId, OrderStatusRequest orderStatusRequest, String jwt) throws Exception;

        public Order updateOrderStatusByDeliveryMan(Long orderId, String status, String jwt) throws Exception;

        public Order updateOrderStatusByRestaurant(Long orderId, String jwt) throws Exception;

        public Order updateOrderStatusByUser(Long orderId, String status, String jwt) throws Exception;

        public List<Order> findOrdersByStatus(String status) throws Exception;

}
