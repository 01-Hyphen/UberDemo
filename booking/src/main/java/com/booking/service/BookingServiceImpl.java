package com.booking.service;

import com.booking.constants.RideStatus;
import com.booking.dto.BookingDTO;
import com.booking.dto.ResponseDTO;
import com.booking.entity.Booking;
import com.booking.entity.Rider;
import com.booking.mapper.BookingMapper;
import com.booking.repo.BookingRepository;
import com.booking.repo.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl {
    private BookingRepository bookingRepository;
    private RiderRepository riderRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,RiderRepository riderRepository){
        this.bookingRepository = bookingRepository;
        this.riderRepository = riderRepository;
    }

    public long bookRide(BookingDTO bookingDTO,long riderId){
        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(()-> new RuntimeException("Rider not found"));

        Booking booking = new Booking();
//        booking.setRider(rider);
//        booking.setPickUpLongitude(bookingDTO.getPickUpLongitude());
//        booking.setPickUpLatitude(bookingDTO.getPickUpLatitude());
//        booking.setDropLatitude(bookingDTO.getDropLatitude());
//        booking.setDropLongitude(bookingDTO.getDropLongitude());
//        booking.setPickupLocation(bookingDTO.getPickupLocation());
//        booking.setDropLocation(bookingDTO.getDropLocation());
//        booking.setRideStatus(RideStatus.REQUESTED);
        BookingMapper.toBooking(bookingDTO,booking);
        booking.setRider(rider);
        Booking savedBooking = bookingRepository.save(booking);
        return savedBooking.getBookingId();

    }

    public List<BookingDTO> getBookings(long riderId){
        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(()-> new RuntimeException("Rider not found"));
        List<BookingDTO> bookingsDTO = rider.getBooking().stream()
                .map(b->{
                   return BookingMapper.toBookingDTO(b,new BookingDTO());
                }).toList();

        if(bookingsDTO.isEmpty()){
            throw new RuntimeException("No bookings for the  rider");
        }
        return bookingsDTO;
    }

    public BookingDTO getCurrentBooking(long riderId){
        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(()-> new RuntimeException("Rider not found"));
        System.out.println(rider.getBooking());
      Booking currentBooking = rider.getBooking().stream().filter(b->b.getRideStatus().equals(RideStatus.REQUESTED))
              .findFirst().get();
        System.out.println(currentBooking);


        return BookingMapper.toBookingDTO(currentBooking,new BookingDTO());
    }

    public long changeStatusByDriver(long bookingId, long driverId,RideStatus rideStatus
    ){
       Booking booking = bookingRepository.findById(bookingId)
               .orElseThrow(()-> new RuntimeException("No booking  find with the given id"));

       booking.setRideStatus(rideStatus);
       Booking updatedBooking = bookingRepository.save(booking);
       return updatedBooking.getBookingId();
    }

    public long cancelBookingByRider(long riderId){
        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(()-> new RuntimeException("Rider not found"));
        Booking booking  = rider.getBooking().stream().filter(b->b.getRideStatus().equals(RideStatus.REQUESTED))
                .findFirst().orElseThrow(()-> new RuntimeException("No Current Requested booking"));
        booking.setRideStatus(RideStatus.CANCELLED);
        return bookingRepository.save(booking).getBookingId();
    }


}
