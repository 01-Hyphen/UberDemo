package com.booking.mapper;

import com.booking.constants.RideStatus;
import com.booking.dto.BookingDTO;
import com.booking.entity.Booking;

public class BookingMapper {

    public static BookingDTO toBookingDTO(Booking booking,BookingDTO bookingDTO){
        bookingDTO.setDropLatitude(booking.getDropLatitude());
        bookingDTO.setDropLongitude(booking.getDropLongitude());
        bookingDTO.setPickupLocation(booking.getPickupLocation());
        bookingDTO.setPickUpLatitude(booking.getPickUpLatitude());
        bookingDTO.setPickUpLongitude(booking.getPickUpLongitude());
        bookingDTO.setDropLocation(booking.getDropLocation());
        bookingDTO.setRideStatus(booking.getRideStatus());
        return bookingDTO;
    }

    public static Booking toBooking(BookingDTO bookingDTO,Booking booking){
        booking.setDropLatitude(bookingDTO.getDropLatitude());
        booking.setDropLongitude(bookingDTO.getDropLongitude());
        booking.setPickupLocation(bookingDTO.getPickupLocation());
        booking.setPickUpLatitude(bookingDTO.getPickUpLatitude());
        booking.setPickUpLongitude(bookingDTO.getPickUpLongitude());
        booking.setDropLocation(bookingDTO.getDropLocation());
        booking.setRideStatus(RideStatus.REQUESTED);
        return booking;
    }

}
