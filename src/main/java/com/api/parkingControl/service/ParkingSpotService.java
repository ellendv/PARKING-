package com.api.parkingControl.service;

import com.api.parkingControl.model.ParkingSpotModel;
import com.api.parkingControl.repository.ParkingSpotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpotService {
    private final ParkingSpotRepository repository;

    public ParkingSpotService(ParkingSpotRepository repository) {
        this.repository = repository;
    }

@Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return repository.save(parkingSpotModel);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return repository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return repository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return repository.existsByApartmentAndBlock(apartment, block);

    }
    public Page<ParkingSpotModel> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Optional<ParkingSpotModel> findByid(Long id) {
        return repository.findById(id);

    }

    @Transactional
    public void delete(ParkingSpotModel parkingSpotModel) {
        repository.delete(parkingSpotModel);
    }
}
