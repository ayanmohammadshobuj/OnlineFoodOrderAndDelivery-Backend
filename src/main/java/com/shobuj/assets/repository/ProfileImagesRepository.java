package com.shobuj.assets.repository;

import com.shobuj.assets.entity.ProfileImages;
import com.shobuj.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImagesRepository extends JpaRepository<ProfileImages, Integer> {
    ProfileImages findByUser(User user);
}