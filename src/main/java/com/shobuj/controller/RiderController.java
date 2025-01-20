package com.shobuj.controller;

import com.shobuj.entity.Rider;
import com.shobuj.entity.User;
import com.shobuj.service.RiderService;
import com.shobuj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rider")
public class RiderController {

    @Autowired
    private RiderService riderService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Rider> createRider(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Rider rider) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Rider rider1 = riderService.createRider(rider, user);
        return ResponseEntity.ok(rider1);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Rider> updateRider(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id,
            @RequestBody Rider rider) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Rider rider1 = riderService.updateRider(id, rider);
        return ResponseEntity.ok(rider1);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Rider> findRiderById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Rider rider = riderService.findRiderById(id);
        return ResponseEntity.ok(rider);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRider(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        riderService.deleteRider(id);
        return ResponseEntity.ok("Rider deleted successfully");
    }

    // Find All Riders
    @GetMapping("/findall")
    public ResponseEntity<List<Rider>> findAllRiders(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Rider> riders = riderService.findAllRiders();
        return ResponseEntity.ok(riders);
    }
}
