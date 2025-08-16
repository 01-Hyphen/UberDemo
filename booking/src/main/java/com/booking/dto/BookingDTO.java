package com.booking.dto;

import com.booking.constants.RideStatus;
import com.booking.entity.Rider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class BookingDTO {
    private String pickupLocation;;
    private String dropLocation;
    private double pickUpLongitude;
    private double pickUpLatitude;
    private double dropLongitude;
    private double dropLatitude;
    private RideStatus rideStatus;
}
