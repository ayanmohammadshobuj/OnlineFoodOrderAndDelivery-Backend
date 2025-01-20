package com.shobuj.service;

import com.shobuj.entity.Rider;
import com.shobuj.entity.User;

import java.util.List;

public interface RiderService {
    public Rider createRider(Rider rider, User user) throws Exception;

    public Rider findRiderById(Long id) throws Exception;

    public Rider updateRider(Long id, Rider rider) throws Exception;

    public void deleteRider(Long id) throws Exception;

    public List<Rider> findAllRiders() throws Exception;


}
