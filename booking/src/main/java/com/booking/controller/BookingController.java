package com.booking.controller;

import com.booking.constants.RideStatus;
import com.booking.dto.BookingDTO;
import com.booking.dto.ResponseDTO;
import com.booking.service.BookingServiceImpl;
import com.booking.exception.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseDTO> bookRide(@RequestBody @Valid BookingDTO bookingDTO, @NotNull(message = "{booking.notnull}") @RequestParam String riderId){
        long id  = bookingService.bookRide(bookingDTO,riderId);
       return new ResponseEntity<>( new ResponseDTO("Your ride has been booked with bookingId: "+id)
       , HttpStatus.OK);

    }

    @GetMapping("/get-bookings")
    public ResponseEntity<List<BookingDTO>> getBookingForRider(@RequestParam @NotNull(message = "{booking.notnull}") String riderId) throws EntityNotFoundException {
        return new ResponseEntity<>(bookingService.getBookings(riderId),HttpStatus
                .OK);
    }

    @GetMapping("/get-current-booking")
    public ResponseEntity<BookingDTO> getCurrentBookingForRider(@RequestParam @NotNull(message = "{booking.notnull}") String riderId){
        return new ResponseEntity<>(bookingService.getCurrentBooking(riderId),HttpStatus
                .OK);
    }

    @PutMapping("/update-status-driver")
    public ResponseEntity<ResponseDTO> changeStatusByDriver(@RequestParam @NotNull(message = "{booking.notnull}") long bookingId,
                                                            @RequestParam @NotNull(message = "{booking.notnull}") long driverId,
                                                             @RequestParam @NotNull(message = "{booking.notnull}") RideStatus rideStatus){
        long id = bookingService.changeStatusByDriver(bookingId,driverId,rideStatus);
        return new ResponseEntity<>(new ResponseDTO("Status has been updated as ${rideStatus} for booking ${id}"),HttpStatus.OK);
    }

    @PutMapping("/cancel-your-ride")
    public ResponseEntity<ResponseDTO> cancelRideBYRider(@RequestParam @NotNull(message = "{booking.notnull}") String riderId){
        long id = bookingService.cancelBookingByRider(riderId);
        return new ResponseEntity<>(new ResponseDTO("Status has been updated as CANCELLED for booking ${id}"),HttpStatus
                .OK);
    }

}


