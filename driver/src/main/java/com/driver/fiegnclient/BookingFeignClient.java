package com.driver.fiegnclient;

import com.driver.constants.RideStatus;
//import com.driver.dto.Booking;
import com.driver.dto.Booking;
import com.driver.dto.ResponseDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "booking")
public interface BookingFeignClient {
    @PutMapping("/booking/update-status-driver")
    public ResponseEntity<ResponseDTO> changeStatusByDriver(@RequestParam @NotNull(message = "{booking.notnull}") long bookingId,
                                                            @RequestParam @NotNull(message = "{booking.notnull}") String driverId,
                                                            @RequestParam @NotNull(message = "{booking.notnull}") RideStatus rideStatus);


    @GetMapping("/booking/getBooking")
    public ResponseEntity<Booking> getBooking(@RequestParam @NotNull(message = "{booking.notnull}") long bookingId);
}
