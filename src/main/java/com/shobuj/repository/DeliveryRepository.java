package com.shobuj.repository;

import com.shobuj.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findDeliveriesByRiderId(Long id);

    Delivery findDeliveryByOrderId(Long id);
}
