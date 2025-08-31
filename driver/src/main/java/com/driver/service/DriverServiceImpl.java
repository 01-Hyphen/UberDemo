package com.driver.service;

import com.driver.constants.RideStatus;
import com.driver.dto.*;
import com.driver.entity.Driver;
import com.driver.entity.Vehicle;
import com.driver.exception.NoBookingAvailableForDriverException;
import com.driver.exception.NoNearbyDriversAvailableException;
import com.driver.exception.RideAlreadyAcceptedException;
import com.driver.fiegnclient.BookingFeignClient;
import com.driver.misc.DriverBookingStore;
import com.driver.repository.DriverRepository;
import com.driver.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private BookingFeignClient bookingFeignClient;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;



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

    public void acceptBooking(String bookingId,String driverId){
        String key = "acceptedBookingId:"+bookingId+":driverId";
        Boolean claimed = redisTemplate.opsForValue().setIfAbsent(key, driverId, Duration.ofSeconds(30));
        if(!claimed){
            throw new RideAlreadyAcceptedException("Ride already Accepted");
        }
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(()-> new EntityNotFoundException("Driver not found with Id"+ driverId));
        driver.setAvailability(false);
        driverRepository.save(driver);
        String payLoad = bookingId+":"+driverId;
        var isSent =  streamBridge.send("accept-booking",payLoad);
        removeBookingFromAllDrivers(bookingId);
    }

    public ResponseDTO changeBookingStatus(long bookingId, String driverId, RideStatus rideStatus){
        String key = "driver:" + driverId + ":booking";
        if(rideStatus.equals(RideStatus.CANCELLED)){
            redisTemplate.delete(key);
            Driver driver = driverRepository.findById(driverId)
                    .orElseThrow(()-> new EntityNotFoundException("Driver not found with Id"+ driverId));
            driver.setAvailability(true);
        }
        ResponseEntity<ResponseDTO> responseDTOResponseEntity = bookingFeignClient.changeStatusByDriver(bookingId, driverId, rideStatus);
        System.out.println(responseDTOResponseEntity.getBody());
        //if status is cancelled then we need to remove the booking from that particular drivers list only

        return responseDTOResponseEntity.getBody();
    }

    public Booking getBooking(long bookingId){
       return bookingFeignClient.getBooking(bookingId).getBody();
    }

}
