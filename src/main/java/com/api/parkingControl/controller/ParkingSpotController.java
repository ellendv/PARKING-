package com.api.parkingControl.controller;

import com.api.parkingControl.model.ParkingSpotModel;
import com.api.parkingControl.model.dto.ParkingSpotDto;
import com.api.parkingControl.service.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("parking")
public class ParkingSpotController {
    private final ParkingSpotService service;

    public ParkingSpotController(ParkingSpotService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> saveParking(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {

        if (service.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
        }
        if (service.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
        }
        if (service.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
        }

        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(parkingSpotModel));
    }

    @GetMapping
    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots(@PageableDefault(page = 0, size = 10, sort = "id",direction = Sort.Direction.ASC)Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParking(@PathVariable(value = "id") Long id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = service.findByid(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParking(@PathVariable(value = "id") Long id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = service.findByid(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found");
        }
        service.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("parking spot deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParking(@PathVariable(value = "id") Long id, @RequestBody @Valid ParkingSpotDto parkingDto) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = service.findByid(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found");
        }
        var parkingSpotModel = parkingSpotModelOptional.get();
        parkingSpotModel.setParkingSpotNumber(parkingDto.getParkingSpotNumber());
        parkingSpotModel.setLicensePlateCar(parkingDto.getLicensePlateCar());
        parkingSpotModel.setModelCar(parkingDto.getModelCar());
        parkingSpotModel.setBrandCar(parkingDto.getBrandCar());
        parkingSpotModel.setColorCar(parkingDto.getColorCar());
        parkingSpotModel.setResponsibleName(parkingDto.getResponsibleName());
        parkingSpotModel.setApartment(parkingDto.getApartment());
        parkingSpotModel.setBlock(parkingDto.getBlock());

        return ResponseEntity.status(HttpStatus.OK).body(service.save(parkingSpotModel));

    }
}
