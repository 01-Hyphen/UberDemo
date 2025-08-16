package com.booking.controller;

import com.booking.constants.RideStatus;
import com.booking.dto.BookingDTO;
import com.booking.dto.ResponseDTO;
import com.booking.entity.Booking;
import com.booking.service.BookingServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingServiceImpl  bookingService;

    @PostMapping("/book-ride")
    public ResponseEntity<ResponseDTO> bookRide(@RequestBody BookingDTO bookingDTO, @RequestParam long riderId){
        long id  = bookingService.bookRide(bookingDTO,riderId);
       return new ResponseEntity<>( new ResponseDTO("Your ride has been booked with bookingId: "+id)
       , HttpStatus.OK);

    }

    @GetMapping("/get-bookings")
    public ResponseEntity<List<BookingDTO>> getBookingForRider(@RequestParam long riderId){
        return new ResponseEntity<>(bookingService.getBookings(riderId),HttpStatus
                .OK);
    }

    @GetMapping("/get-current-booking")
    public ResponseEntity<BookingDTO> getCurrentBookingForRider(@RequestParam long riderId){
        return new ResponseEntity<>(bookingService.getCurrentBooking(riderId),HttpStatus
                .OK);
    }

    @PutMapping("/update-status-driver")
    public ResponseEntity<ResponseDTO> changeStatusByDriver(@RequestParam long bookingId, @RequestParam long driverId,
                                                             @RequestParam RideStatus rideStatus){
        long id = bookingService.changeStatusByDriver(bookingId,driverId,rideStatus);
        return new ResponseEntity<>(new ResponseDTO("Status has been updated as ${rideStatus} for booking ${id}"),HttpStatus.OK);
    }

    @PutMapping("/cancel-your-ride")
    public ResponseEntity<ResponseDTO> cancelRideBYRider(@RequestParam long riderId){
        long id = bookingService.cancelBookingByRider(riderId);
        return new ResponseEntity<>(new ResponseDTO("Status has been updated as CANCELLED for booking ${id}"),HttpStatus
                .OK);
    }

}


