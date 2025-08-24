package com.driver.functions;

import com.driver.dto.BookingCreatedEvent;
import com.driver.dto.UserRegistrationEvent;
import com.driver.service.DriverServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class DriverFunctions {

    @Autowired
    DriverServiceImpl driverService;


    @Bean
   public Consumer<BookingCreatedEvent> fetchBooking(){
        return msg->{
            System.out.println(msg.getPickupLocation());
            driverService.addBooking(msg);
        };

   }

    @Bean
    public Consumer<UserRegistrationEvent> addDriver(){
        return user->{
            if(user.getRoles().contains("DRIVER")){
                System.out.println(user.getUserName());
                driverService.addDriver(user);
            }

        };

    }

}
