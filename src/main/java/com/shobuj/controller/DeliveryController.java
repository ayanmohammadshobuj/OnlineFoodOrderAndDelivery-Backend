package com.shobuj.controller;

import com.shobuj.entity.Delivery;
import com.shobuj.entity.Rider;
import com.shobuj.entity.User;
import com.shobuj.service.DeliveryService;
import com.shobuj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private UserService userService;

    // Create Delivery
    @PostMapping("/create/{id}")
    public ResponseEntity<Delivery> createDelivery(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id,
            @RequestBody Delivery delivery) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Delivery delivery1 = deliveryService.createDelivery(id, delivery, user);
        return ResponseEntity.ok(delivery1);
    }


    // Picked Up Delivery
    @PutMapping("/picked-up/{id}")
    public ResponseEntity<Delivery> pickedUpDelivery(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Delivery delivery = deliveryService.pickedUpDelivery(id);
        return ResponseEntity.ok(delivery);
    }

    // Delivered Delivery
    @PutMapping("/delivered/{id}/{otp}")
    public ResponseEntity<Delivery> deliveredDelivery(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id,
            @PathVariable int otp) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Delivery delivery = deliveryService.deliveredDelivery(id, otp);
        return ResponseEntity.ok(delivery);
    }

    // Update Delivery
    @PutMapping("/update/{id}")
    public ResponseEntity<Delivery> updateDelivery(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id,
            @RequestBody Delivery delivery) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Delivery delivery1 = deliveryService.updateDelivery(id, delivery);
        return ResponseEntity.ok(delivery1);
    }

    // Update Delivery Status
    @PutMapping("/update-status/{id}")
    public ResponseEntity<Delivery> updateDeliveryStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id,
            @RequestBody Delivery delivery) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Delivery delivery1 = deliveryService.updateDeliveryStatus(id, delivery);
        return ResponseEntity.ok(delivery1);
    }

    // Find All Deliveries
    @GetMapping("/all")
    public ResponseEntity<List<Delivery>> findAllDeliveries() throws Exception {
        List<Delivery> deliveries = deliveryService.findAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }

    // Find Delivery By Rider Id
    @GetMapping("/rider")
    public ResponseEntity<List<Delivery>> findDeliveriesByRiderId(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Rider rider = user.getRider();
        List<Delivery> deliveries = deliveryService.findDeliveriesByRiderId(rider.getId());
        return ResponseEntity.ok(deliveries);
    }

    // Delete Delivery
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDelivery(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        deliveryService.deleteDelivery(id);
        return ResponseEntity.ok("Delivery deleted successfully");
    }

    // Find Delivery By Order Id
    @GetMapping("/order/{id}")
    public ResponseEntity<Delivery> findDeliveryByOrderId(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Delivery delivery = deliveryService.findDeliveryByOrderId(id);
        return ResponseEntity.ok(delivery);
    }

}
