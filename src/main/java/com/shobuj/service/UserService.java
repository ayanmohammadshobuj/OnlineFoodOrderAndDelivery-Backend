package com.shobuj.service;

import com.shobuj.entity.User;

import java.util.List;

public interface UserService {
    public User findUserByJwtToken(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public List<User> findAllUsers();
    public User updateUserProfile(String jwt, User user) throws Exception;
    public User updateUserById(Long id, User user) throws Exception;
    public User deleteUserById(Long id) throws Exception;
}


