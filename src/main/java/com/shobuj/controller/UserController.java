package com.shobuj.controller;

import com.shobuj.entity.User;
import com.shobuj.repository.UserRepository;
import com.shobuj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<User> updateUserProfile(@RequestHeader("Authorization") String jwt, @RequestBody User user) throws Exception {
        User updatedUser = userService.updateUserProfile(jwt, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // update user by id
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User user) throws Exception {
        User updatedUser = userService.updateUserById(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<User> deleteUserById(@PathVariable Long id) throws Exception {
//        User deletedUser = userService.deleteUserById(id);
//        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
//    }


}
