package com.booking.entity;

import com.booking.constants.RideStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
@Entity
@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class Booking extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingId;
    private double pickUpLongitude;
    private double pickUpLatitude;
    private double dropLongitude;
    private double dropLatitude;
    private String pickupLocation;;
    private String dropLocation;
    @ManyToOne
    @JoinColumn(name="rider_id",referencedColumnName = "riderId")
    @JsonIgnore
    private Rider rider;
    private LocalDateTime pickupTime;
    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;
}
