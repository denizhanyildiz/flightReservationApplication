package com.flight.ReservationService.Dto;

import com.flight.ReservationService.Entity.Enum.Situation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class UpdateFlightRequestDto {
    private String flightName;
    private String flightDescription;
    private Situation situation;
    private LocalDateTime plannedTime;
    private String takeoffAirportCode;
    private String arrivalAirportCode;
    private String planeCode;
}
