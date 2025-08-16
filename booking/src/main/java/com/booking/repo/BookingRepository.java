package com.booking.repo;

import com.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query("select b from Booking b where b.rider= :riderId")
    List<Booking> findBookingWithRiderId(@Param("riderId") long riderId);

}
