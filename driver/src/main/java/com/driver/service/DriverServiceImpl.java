package com.driver.service;

import com.driver.dto.BookingCreatedEvent;
import com.driver.dto.UserRegistrationEvent;
import com.driver.dto.VehicleDto;
import com.driver.entity.Driver;
import com.driver.entity.Vehicle;
import com.driver.exception.NoBookingAvailableForDriverException;
import com.driver.exception.NoNearbyDriversAvailableException;
import com.driver.misc.DriverBookingStore;
import com.driver.repository.DriverRepository;
import com.driver.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DriverServiceImpl {
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private DriverLocationService driverLocationService;

    @Autowired
    private DriverBookingStore driverBookingStore;



    public void addBooking( BookingCreatedEvent bookingCreatedEvent){
        double pickupLat = bookingCreatedEvent.getPickUpLatitude();
        double pickupLon = bookingCreatedEvent.getPickUpLongitude();
        List<String> nearbyDrivers = driverLocationService.findNearbyDrivers(pickupLat, pickupLon);
        System.out.println(nearbyDrivers);
        if(nearbyDrivers.isEmpty()){
            throw new NoNearbyDriversAvailableException();
        }
        for (String driverId: nearbyDrivers){
            Driver driver = driverRepository.findById(driverId).orElseThrow(()-> new EntityNotFoundException());
            if(driver.isAvailability()){
                driverBookingStore.addBooking(driverId,bookingCreatedEvent.getBookingId());
            }
        }
//        nearbyDrivers.stream()
//                .filter(driverId->driverRepository.findById(driverId).get().isAvailability())
//                .forEach(driverId->{
//                    System.out.println(driverId);
//                driverBookingStore.addBooking(driverId,bookingCreatedEvent.getBookingId());
//                });
    }

    public  String getBooking(String  driverId){
            String bookingId = driverBookingStore.getBookings(driverId);
            if(bookingId == null ||bookingId.isEmpty()){
                throw new NoBookingAvailableForDriverException();
            }
        return "B"+bookingId;
    }

    public void removeBookingFromAllDrivers(String bookingId){
        this.driverBookingStore.removeBookingFromAllDrivers(bookingId);
    }

    public void addDriver(UserRegistrationEvent userRegistrationEvent){
        Driver driver = new Driver();
        driver.setDriverId("D"+userRegistrationEvent.getUserId());
        driver.setName(userRegistrationEvent.getUserName());
        driver.setAvailability(true);
        driverRepository.save(driver);
    }

    public void addVehicle(VehicleDto vehicleDto,String driverId){
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(vehicleDto.getMake());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setNumber(vehicleDto.getNumber());
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(()->new EntityNotFoundException("Driver not found with Id"));
        driver.setVehicle(vehicle);
        driverRepository.save(driver);
    }

}
