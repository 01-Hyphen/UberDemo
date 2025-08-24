package com.booking.dto;

import com.booking.constants.RideStatus;
import com.booking.entity.Rider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class BookingDTO {
    
    @NotNull(message = "{booking.notnull}")
    private String pickupLocation;;
    @NotNull(message = "{booking.notnull}")
    private String dropLocation;
    @NotNull(message = "{booking.notnull}")
    private double pickUpLongitude;
    @NotNull(message = "{booking.notnull}")
    private double pickUpLatitude;
    @NotNull(message = "{booking.notnull}")
    private double dropLongitude;
    @NotNull(message = "{booking.notnull}")
    private double dropLatitude;
    private RideStatus rideStatus;
}
