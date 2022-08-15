package com.api.parkingControl.repository;

import com.api.parkingControl.model.ParkingSpotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID> {

}
