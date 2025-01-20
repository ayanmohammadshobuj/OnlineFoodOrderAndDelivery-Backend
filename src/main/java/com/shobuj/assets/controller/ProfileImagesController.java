package com.shobuj.assets.controller;

import com.shobuj.assets.entity.ProfileImages;
import com.shobuj.assets.repository.ProfileImagesRepository;
import com.shobuj.config.JwtProvider;
import com.shobuj.entity.User;
import com.shobuj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/user/images")
public class ProfileImagesController {

    @Autowired
    private ProfileImagesRepository profileImagesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ProfileImages> createProfileImage(@RequestPart("file") MultipartFile file) throws Exception {
        // Extract the user information from the JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the user in the database
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }

        // Create and save the ProfileImages entity
        ProfileImages profileImages = ProfileImages.builder()
                .displayPicture(file.getBytes())
                .user(user)
                .build();

        profileImagesRepository.save(profileImages);
        profileImages.setDisplayPicture(null); // Clear the image data for the response

        return ResponseEntity.ok(profileImages);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<ProfileImages> createProfileImageByUserId(@PathVariable Long userId, @RequestPart("file") MultipartFile file) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        ProfileImages profileImages = ProfileImages.builder()
                .displayPicture(file.getBytes())
                .user(user)
                .build();
        profileImagesRepository.save(profileImages);
        profileImages.setDisplayPicture(null); // Clear the image data for the response
        return ResponseEntity.ok(profileImages);
    }

    @GetMapping
    public ResponseEntity<List<ProfileImages>> getAllProfileImage() {
        List<ProfileImages> profileImagesList = profileImagesRepository.findAll();
        return ResponseEntity.ok(profileImagesList);
    }

    @PutMapping(consumes = "multipart/form-data")
    public ResponseEntity<ProfileImages> updateProfileImage(@RequestPart("file") MultipartFile file) throws Exception {
        // Extract the user information from the JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the user in the database
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }

        // Find the existing ProfileImages entity
        ProfileImages profileImages = profileImagesRepository.findByUser(user);
        if (profileImages == null) {
            throw new Exception("Profile image not found");
        }

        // Update and save the ProfileImages entity
        profileImages.setDisplayPicture(file.getBytes());
        profileImagesRepository.save(profileImages);
        profileImages.setDisplayPicture(null); // Clear the image data for the response

        return ResponseEntity.ok(profileImages);
    }

    // Get the profile image by user id
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileImages> getProfileImageByUserId(@PathVariable Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        ProfileImages profileImages = profileImagesRepository.findByUser(user);
        if (profileImages == null) {
            throw new Exception("Profile image not found");
        }
        profileImages.setDisplayPicture(null); // Clear the image data for the response
        return ResponseEntity.ok(profileImages);
    }

    // Get ther profile image jwt token
    @GetMapping("/jwt")
    public ResponseEntity<ProfileImages> getProfileImageByJwt() throws Exception {
        // Extract the user information from the JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the user in the database
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }

        ProfileImages profileImages = profileImagesRepository.findByUser(user);
        if (profileImages == null) {
            throw new Exception("Profile image not found");
        }
        profileImages.setDisplayPicture(profileImages.getDisplayPicture()); // Clear the image data for the response
        return ResponseEntity.ok(profileImages);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ProfileImages> updateProfileImageByUserId(@PathVariable Long userId, @RequestPart("file") MultipartFile file) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        ProfileImages profileImages = profileImagesRepository.findByUser(user);
        if (profileImages == null) {
            throw new Exception("Profile image not found");
        }
        profileImages.setDisplayPicture(file.getBytes());
        profileImagesRepository.save(profileImages);
        profileImages.setDisplayPicture(null); // Clear the image data for the response
        return ResponseEntity.ok(profileImages);
    }
}