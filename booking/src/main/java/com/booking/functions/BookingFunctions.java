package com.booking.functions;

import com.booking.dto.UserRegistrationEvent;
import com.booking.entity.Rider;
import com.booking.repo.RiderRepository;
import com.booking.service.BookingServiceImpl;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class BookingFunctions {

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    BookingServiceImpl bookingService;

    @Bean
    public Consumer<UserRegistrationEvent> persistUserInBookingDB(){
        return  (userRegistrationEvent)->{
            if(userRegistrationEvent.getRoles().contains("RIDER")) {
                Rider rider = new Rider();
                rider.setRiderId("R"+userRegistrationEvent.getUserId());
                rider.setRiderName(userRegistrationEvent.getUserName());
                riderRepository.save(rider);
            }
        };
    }
    @Bean
    public Consumer<String> acceptBooking(){
        return msg->{
            String[] payload = msg.split(":");
            String bookingId = payload[0].substring(1);
            String driverId = payload[1];
            bookingService.acceptBooking(bookingId,driverId);

        };
    }

}
