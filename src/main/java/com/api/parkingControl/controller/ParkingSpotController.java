package com.api.parkingControl.controller;

import com.api.parkingControl.service.ParkingSpotService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("parking")
public class ParkingSpotController {
    private final ParkingSpotService service;

    public ParkingSpotController(ParkingSpotService service) {
        this.service = service;
    }


}
