package com.booking.dto;

import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RiderDTO {
    @Id
    private long riderId;
    private String riderName;
}
