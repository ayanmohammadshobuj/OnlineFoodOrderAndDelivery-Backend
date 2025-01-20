package com.shobuj.service;

import com.shobuj.entity.Delivery;
import com.shobuj.entity.User;

import java.util.List;

public interface DeliveryService {
    public Delivery createDelivery(Long id, Delivery delivery, User user) throws Exception;

    public Delivery pickedUpDelivery(Long id) throws Exception;

    public Delivery deliveredDelivery(Long id, int otp) throws Exception;

    public Delivery findDeliveryById(Long id) throws Exception;

    public Delivery updateDelivery(Long id, Delivery delivery) throws Exception;

    public Delivery updateDeliveryStatus(Long id, Delivery delivery) throws Exception;

    public void deleteDelivery(Long id) throws Exception;

    public List<Delivery> findAllDeliveries() throws Exception;

    public List<Delivery> findDeliveriesByRiderId(Long id) throws Exception;

    public Delivery findDeliveryByOrderId(Long id) throws Exception;
}
