package com.booking.functions;

import com.booking.dto.UserRegistrationEvent;
import com.booking.entity.Rider;
import com.booking.repo.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class BookingFunctions {

    @Autowired
    RiderRepository riderRepository;

    @Bean
    public Consumer<UserRegistrationEvent> persistUserInBookingDB(){
        return  (userRegistrationEvent)->{
            if(userRegistrationEvent.getRoles().contains("RIDER")) {
                Rider rider = new Rider();
                rider.setRiderId(userRegistrationEvent.getUserId());
                rider.setRiderName(userRegistrationEvent.getUserName());
                riderRepository.save(rider);
            }

        };
    }

}
