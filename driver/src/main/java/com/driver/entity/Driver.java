package com.driver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter@Setter
public class Driver {

    public Driver(){}

    @Id
    private String driverId;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id",referencedColumnName = "vehicleId")
    private Vehicle vehicle;
    private boolean availability;
    @Transient
    private double lat;
    @Transient
    private double lon;
}
