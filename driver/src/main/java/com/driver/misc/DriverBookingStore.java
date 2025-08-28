package com.driver.misc;

import com.driver.dto.BookingCreatedEvent;
import com.driver.exception.DriverAlreadyHasBookingAssignException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Component
public class DriverBookingStore {
    private final RedisTemplate<String, String> redisTemplate;

    public DriverBookingStore(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String addBooking(String driverId,Long bookingId){
        String key = "driver:" + driverId + ":booking";
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(bookingId), Duration.ofMinutes(30));
        System.out.println(success);
        if (Boolean.FALSE.equals(success)) {
            throw new DriverAlreadyHasBookingAssignException("b"+bookingId);
        }
        return "Stored booking " + bookingId + " for driver " + driverId;
    }

    public String getBookings(String driverId) {
        String key = "driver:" + driverId + ":booking";
        return redisTemplate.opsForValue().get(key);
    }

    public void removeBookingFromAllDrivers(String bookingId){
        List<String> drivers = getDriverBookingKeys().stream().toList();
        if(null != drivers){
            for(String key : drivers){
                String value = redisTemplate.opsForValue().get(key);
                if(value.equals(bookingId)){
                    redisTemplate.delete(key);
                    System.out.println("Deleted key: " + key + " with value: " + value);
                }
            }
        }
    }

    public Set<String> getDriverBookingKeys() {
        return redisTemplate.keys("driver:*:booking");
    }
}
