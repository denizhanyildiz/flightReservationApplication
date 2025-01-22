package com.flight.ReservationService.Dto.Request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateFlightRequestDto {
    private String flightName;
    private String flightDescription;
    private LocalDateTime plannedTime;
    private String takeoffAirportCode;
    private String arrivalAirportCode;
    private String planeCode;
}
