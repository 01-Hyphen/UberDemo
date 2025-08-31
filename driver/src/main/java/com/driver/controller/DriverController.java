package com.driver.controller;

import com.driver.constants.RideStatus;
import com.driver.dto.*;
import com.driver.entity.Driver;
import com.driver.misc.DriverBookingStore;
import com.driver.repository.DriverRepository;
import com.driver.service.DriverLocationService;
import com.driver.service.DriverServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/drivers")
@CrossOrigin
public class DriverController {

    private final DriverBookingStore bookingStore;
    @Autowired
    private DriverServiceImpl driverService;

    @Autowired
    private DriverLocationService driverLocationService;


    public DriverController(DriverBookingStore bookingStore) {
        this.bookingStore = bookingStore;
    }



    @PostMapping("/location")
    public ResponseEntity<ResponseDTO> updateLocation(@RequestBody @Valid LocationDTO locationDTO){
        driverLocationService.updateDriverLocation(locationDTO.getDriverId(),locationDTO.getLat(),locationDTO.getLon());
        return new ResponseEntity<>(new ResponseDTO("Location updated!",HttpStatus.OK),HttpStatus.OK);
    }

    @MessageMapping("/driver/location")
    public void receiveLocation(LocationDTO locationDTO){
        System.out.println("Received location from driver: "+locationDTO);
        driverLocationService.updateDriverLocation(locationDTO.getDriverId(),locationDTO.getLat(),locationDTO.getLon());
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<String>> getNearbyDrivers(@RequestParam @NotNull(message = "{driver.notnull}" ) double lat,
                                                         @RequestParam  @NotNull(message = "{driver.notnull}" ) double lon){
        return ResponseEntity.ok(driverLocationService.findNearbyDrivers(lat,lon));
    }
    @PostMapping("/addbooking")
    public ResponseEntity<ResponseDTO> addBooking(@RequestBody BookingCreatedEvent bookingCreatedEvent){
        this.driverService.addBooking(bookingCreatedEvent);
        return new ResponseEntity<>(new ResponseDTO("Booking Added Successfully!",HttpStatus.OK),HttpStatus.OK);
    }


    // Fetch pending bookings for a driver
    @GetMapping("/bookings")
    public  ResponseEntity<ResponseDTO> getDriverBookings(@RequestParam  @NotNull(message = "{driver.notnull}" )  String driverId) {
        String bookingId = this.driverService.getBooking(driverId);
        return new ResponseEntity<>(new ResponseDTO("Booking with bookingId: "+bookingId,HttpStatus.OK),HttpStatus.OK);
    }

    @GetMapping("/getAllDriverBookingKeys")
    public ResponseEntity<Set<String>> getAllDriverBookingKeys( ) {
        Set<String> allBookingsIn_30M = bookingStore.getDriverBookingKeys();
        return new ResponseEntity<>(allBookingsIn_30M,HttpStatus.OK);
    }

    @DeleteMapping("/removeBooking")
    public ResponseEntity<ResponseDTO> removeBookingFromAllDrivers(@RequestParam  @NotNull(message = "{driver.notnull}" ) String bookingId){
        this.driverService.removeBookingFromAllDrivers(bookingId);
        return new ResponseEntity<>(new ResponseDTO("All Bookings removed for this bookingId from all drivers queues",HttpStatus.OK),HttpStatus.OK);
    }

    @Autowired
    DriverRepository driverRepository;

    @GetMapping("/getDriver/{id}")
    public Driver getDriver(@PathVariable  @NotNull(message = "{driver.notnull}" ) String id){
        return driverRepository.findById(id).get();
    }

    @PostMapping("/addDriver")
    public ResponseEntity<ResponseDTO> addDriver(@RequestBody UserRegistrationEvent userRegistrationEvent){
        this.driverService.addDriver(userRegistrationEvent);
        return new ResponseEntity<>(new ResponseDTO("Driver Added Successfully!",HttpStatus.OK),HttpStatus.OK);
    }
    @PostMapping("/addVehicle")
    public ResponseEntity<ResponseDTO> addBooking(@RequestBody VehicleDto vehicleDto, @RequestParam String driverId){
        this.driverService.addVehicle(vehicleDto,driverId);
        return new ResponseEntity<>(new ResponseDTO("Vehicle Added Successfully!",HttpStatus.OK),HttpStatus.OK);
    }

    @PostMapping("/accept-booking")
    public ResponseEntity<ResponseDTO> acceptBooking(@RequestParam String bookingId, @RequestParam String driverId){
        driverService.acceptBooking(bookingId,driverId);
        return new ResponseEntity<>(new ResponseDTO("Booking acception initiated",HttpStatus.OK),HttpStatus.OK);
    }

    @PutMapping("/change-booking-status")
    public ResponseEntity<ResponseDTO> changeBookingStatus(@RequestParam @NotNull(message = "{booking.notnull}") long bookingId,
                                                           @RequestParam @NotNull(message = "{booking.notnull}") String driverId,
                                                           @RequestParam @NotNull(message = "{booking.notnull}") RideStatus rideStatus){
       return new ResponseEntity<>(driverService.changeBookingStatus(bookingId,driverId,rideStatus),HttpStatus.OK);
    }

    @GetMapping("/get-booking")
    public ResponseEntity<Booking> getBooking(@RequestParam long bookingId){
       return new ResponseEntity<>(driverService.getBooking(bookingId),HttpStatus.OK);
    }

}

