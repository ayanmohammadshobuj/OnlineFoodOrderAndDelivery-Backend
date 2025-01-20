package com.shobuj.service.impl;

import com.shobuj.entity.*;
import com.shobuj.enums.DeliveryStatus;
import com.shobuj.enums.OrderStatus;
import com.shobuj.repository.DeliveryRepository;
import com.shobuj.repository.OrderRepository;
import com.shobuj.repository.RestaurantRepository;
import com.shobuj.service.DeliveryService;
import com.shobuj.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;



    private static final Logger logger = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    @Override
    public Delivery createDelivery(Long id, Delivery delivery, User user) throws Exception {
        logger.info("Creating delivery for order ID: {}", id);
        Rider rider = user.getRider();
        Order order = orderService.findOrderById(id);
        if (order == null) {
            logger.error("Order not found for ID: {}", id);
            throw new Exception("Order not found");
        }

        Restaurant restaurant = order.getRestaurant();
        if (restaurant == null) {
            logger.error("Restaurant not found for order ID: {}", id);
            throw new Exception("Restaurant not found");
        }

        Delivery delivery1 = new Delivery();
        delivery1.setCustomerName(order.getCustomer().getFullName());
        delivery1.setCustomerAddress(order.getApartment() + ", " + order.getStreet() + "-" + order.getCity());
        delivery1.setLandmark(order.getLandmark());
        delivery1.setCustomerPhone(order.getPhone());

        RestaurantAddress restaurantAddress = restaurant.getRestaurantAddress();
        RestaurantContactInformation restaurantContactInformation = restaurant.getRestaurantContactInformation();

        delivery1.setRestaurantName(restaurant.getName());
        delivery1.setRestaurantAddress(restaurantAddress.getFullAddress() + ", " + restaurantAddress.getStreet() + ", " + restaurantAddress.getCity() + "-" + restaurantAddress.getPostalCode());
        delivery1.setRestaurantPhone(restaurantContactInformation.getMobile());

        delivery1.setRider(rider);
        delivery1.setStatus(DeliveryStatus.ACCEPTED);
        delivery1.setOrder(order);

        order.setOrderStatus(OrderStatus.PREPARING);

        // Generate auto 6 digit OTP
        delivery1.setOTP((int) (Math.random() * 900000 + 100000));

        logger.info("Saving delivery for order ID: {}", id);
        return deliveryRepository.save(delivery1);
    }

    @Override
    public Delivery pickedUpDelivery(Long id) throws Exception {
        Delivery delivery = findDeliveryById(id);
        Order order = deliveryRepository.findById(id).get().getOrder();
        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            delivery.setStatus(DeliveryStatus.CANCELLED);
            return deliveryRepository.save(delivery);
        } else if (order.getOrderStatus() == OrderStatus.COMPLETED) {
            delivery.setStatus(DeliveryStatus.DELIVERED);
            return deliveryRepository.save(delivery);
        } else if (order.getOrderStatus() == OrderStatus.PREPARING) {
            throw new Exception("Order is not ready yet");
        } else if (order.getOrderStatus() == OrderStatus.OUT_FOR_DELIVERY) {
            throw new Exception("Order is already out for delivery");
        }

        delivery.setStatus(DeliveryStatus.PICKED_UP);
        delivery.setDeliveryAcceptedTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery deliveredDelivery(Long id, int otp) throws Exception {
        Delivery delivery = findDeliveryById(id);
        if (delivery.getOTP() != otp) {
            throw new Exception("Invalid OTP");
        }
        delivery.setStatus(DeliveryStatus.DELIVERED);
        delivery.setDeliveryCompletedTime(LocalDateTime.now());
        Order order = deliveryRepository.findById(id).get().getOrder();
        order.setOrderStatus(OrderStatus.COMPLETED);
        return deliveryRepository.save(delivery);
    }


    @Override
    public Delivery findDeliveryById(Long id) throws Exception {
        return deliveryRepository.findById(id).orElseThrow(() -> new Exception("Delivery not found"));
    }

    @Override
    public Delivery updateDelivery(Long id, Delivery delivery) throws Exception {
        Delivery delivery1 = deliveryRepository.findById(id).orElseThrow(() -> new Exception("Delivery not found"));
        delivery1.setStatus(delivery.getStatus());
        delivery1.setDeliveryAcceptedTime(delivery.getDeliveryAcceptedTime());
        delivery1.setDeliveryCompletedTime(delivery.getDeliveryCompletedTime());
        delivery1.setOTP(delivery.getOTP());
        delivery1.setRider(delivery.getRider());
        delivery1.setOrder(delivery.getOrder());
        return deliveryRepository.save(delivery1);
    }

    @Override
    public Delivery updateDeliveryStatus(Long id, Delivery delivery) throws Exception {
        Delivery delivery1 = deliveryRepository.findById(id).orElseThrow(() -> new Exception("Delivery not found"));
        delivery1.setStatus(delivery.getStatus());
        return deliveryRepository.save(delivery1);
    }


    @Override
    public void deleteDelivery(Long id) throws Exception {
        if (deliveryRepository.findById(id).isEmpty()) {
            throw new Exception("Delivery not found");
        }
        deliveryRepository.deleteById(id);
    }

    @Override
    public List<Delivery> findAllDeliveries() throws Exception {
        return deliveryRepository.findAll();
    }

    @Override
    public List<Delivery> findDeliveriesByRiderId(Long id) throws Exception {
        return deliveryRepository.findDeliveriesByRiderId(id);
    }

    @Override
    public Delivery findDeliveryByOrderId(Long id) throws Exception {
        return deliveryRepository.findDeliveryByOrderId(id);
    }
}
