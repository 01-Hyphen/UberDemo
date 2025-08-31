package com.driver.dto;

import com.driver.constants.RideStatus;
import com.driver.constants.RideStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class Booking{

    private long bookingId;
    private double pickUpLongitude;
    private double pickUpLatitude;
    private double dropLongitude;
    private double dropLatitude;
    private String pickupLocation;;
    private String dropLocation;
    private LocalDateTime pickupTime;

    private RideStatus rideStatus;
    private String driverId;
}

