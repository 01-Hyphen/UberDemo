package com.driver.dto;

import lombok.Data;

@Data
public class BookingCreatedEvent {
    private long bookingId;
    private String pickupLocation;;
    private String dropLocation;
    private double pickUpLongitude;
    private double pickUpLatitude;
    private double dropLongitude;
    private double dropLatitude;
}
