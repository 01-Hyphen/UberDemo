package com.booking.service;

import com.booking.constants.RideStatus;
import com.booking.dto.BookingDTO;
import com.booking.entity.Booking;
import com.booking.entity.Rider;
import com.booking.exception.RideAlreadyAcceptedException;
import com.booking.mapper.BookingMapper;
import com.booking.repo.BookingRepository;
import com.booking.repo.RiderRepository;
import com.booking.exception.EntityNotFoundException;
import com.booking.exception.NoBookingsFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.Duration;
import java.util.List;

@Service
@Transactional
public class BookingServiceImpl {
    @Autowired
    private StreamBridge streamBridge;

    private BookingRepository bookingRepository;
    private RiderRepository riderRepository;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,RiderRepository riderRepository){
        this.bookingRepository = bookingRepository;
        this.riderRepository = riderRepository;
    }

    public long bookRide(BookingDTO bookingDTO,String riderId){
        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(()-> new EntityNotFoundException("Rider","riderId",riderId));

        Booking booking = new Booking();
        BookingMapper.toBooking(bookingDTO,booking);
        booking.setRider(rider);
        Booking savedBooking = bookingRepository.save(booking);
        streamBridge.send("send-booking",savedBooking);

        return savedBooking.getBookingId();

    }

    public List<BookingDTO> getBookings(String riderId) throws EntityNotFoundException {
        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(()-> new EntityNotFoundException("Rider","riderId",riderId));
        List<BookingDTO> bookingsDTO = rider.getBooking().stream()
                .map(b->{
                   return BookingMapper.toBookingDTO(b,new BookingDTO());
                }).toList();

        if(bookingsDTO.isEmpty()){
            throw new NoBookingsFoundException("No bookings for the  rider");
        }
        return bookingsDTO;
    }

    public BookingDTO getCurrentBooking(String riderId){
        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(()-> new EntityNotFoundException("Rider","riderId",riderId));
        System.out.println(rider.getBooking());
      Booking currentBooking = rider.getBooking().stream().filter(b->b.getRideStatus().equals(RideStatus.REQUESTED))
              .findFirst().get();
        System.out.println(currentBooking);


        return BookingMapper.toBookingDTO(currentBooking,new BookingDTO());
    }

    public long changeStatusByDriver(long bookingId, String driverId,RideStatus rideStatus
    ){
       Booking booking = bookingRepository.findById(bookingId)
               .orElseThrow(()-> new NoBookingsFoundException("No booking  find with the given id"));

       booking.setRideStatus(rideStatus);
       Booking updatedBooking = bookingRepository.save(booking);
       return updatedBooking.getBookingId();
    }

    public long cancelBookingByRider(String riderId){
        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(()-> new EntityNotFoundException("Rider","riderId",riderId));
        Booking booking  = rider.getBooking().stream().filter(b->b.getRideStatus().equals(RideStatus.REQUESTED))
                .findFirst().orElseThrow(()-> new NoBookingsFoundException("No Current Requested booking"));
        booking.setRideStatus(RideStatus.CANCELLED);
        return bookingRepository.save(booking).getBookingId();
    }


    public String acceptBooking(String bookingId, String driverId) {
        Booking booking = bookingRepository.findById(Long.parseLong(bookingId)).orElseThrow(()-> new EntityNotFoundException("Booking","bookingId",bookingId));
        booking.setDriverId(driverId);
        booking.setRideStatus(RideStatus.ACCEPTED);
        Booking savedBooking = bookingRepository.save(booking);
        return "Booking save with driverId"+driverId;
    }

    public Booking getBooking(long bookingId){
        Booking booking =  bookingRepository.findById(bookingId).orElseThrow(()-> new EntityNotFoundException("Booking","bookingId",String.valueOf(bookingId)));
        return booking;
    }
}
