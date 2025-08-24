package com.booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rider {
    @Id
    private String riderId;
    private String riderName;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "rider")
    private List<Booking> booking;
}
