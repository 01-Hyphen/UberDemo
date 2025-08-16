package com.booking;

import com.booking.dto.ResponseDTO;
import com.booking.service.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "bookingAuditor")
public class BookingApplication  {
	@Autowired
	BookingServiceImpl bookingService;
	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println(currentBookings(2));
//	}
//
//	public static ResponseEntity<ResponseDTO> currentBookings(long riderId){
//		return this.bookingService.getCurrentBooking(2);
//	}
}
