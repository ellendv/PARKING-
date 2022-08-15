package com.api.parkingControl.service;

import com.api.parkingControl.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotService {
    private final ParkingSpotRepository repository;

    public ParkingSpotService(ParkingSpotRepository repository) {
        this.repository = repository;
    }


}
