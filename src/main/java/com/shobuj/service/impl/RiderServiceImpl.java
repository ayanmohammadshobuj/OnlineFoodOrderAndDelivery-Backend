package com.shobuj.service.impl;

import com.shobuj.entity.Rider;
import com.shobuj.entity.User;
import com.shobuj.repository.RiderRepository;
import com.shobuj.service.RiderService;
import com.shobuj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RiderServiceImpl implements RiderService {

    @Autowired
    private UserService userService;

    @Autowired
    private RiderRepository riderRepository;

    @Override
    public Rider createRider(Rider rider, User user) throws Exception {
        Rider rider1 = new Rider();
        if (user == null) {
            throw new Exception("User not found");
        }
        rider1.setUser(user);
        rider1.setName(rider.getName());
        rider1.setPhone(rider.getPhone());
        return riderRepository.save(rider1);
    }

    @Override
    public Rider findRiderById(Long id) throws Exception {
        Optional<Rider> rider = riderRepository.findById(id);
        if (rider.isEmpty()) {
            throw new Exception("Rider not found");
        }
        return rider.get();
    }

    @Override
    public Rider updateRider(Long id, Rider rider) throws Exception {
        Optional<Rider> rider1 = riderRepository.findById(id);
        if (rider1.isEmpty()) {
            throw new Exception("Rider not found");
        }
        Rider rider2 = rider1.get();
        rider2.setName(rider.getName());
        rider2.setPhone(rider.getPhone());
        return riderRepository.save(rider2);
    }

    @Override
    public void deleteRider(Long id) throws Exception {
        Optional<Rider> rider = riderRepository.findById(id);
        if (rider.isEmpty()) {
            throw new Exception("Rider not found");
        }
        riderRepository.deleteById(id);
    }

    @Override
    public List<Rider> findAllRiders() throws Exception {
        return riderRepository.findAll();
    }


}
