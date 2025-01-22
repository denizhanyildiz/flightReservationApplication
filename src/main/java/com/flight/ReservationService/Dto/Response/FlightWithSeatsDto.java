package com.flight.ReservationService.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightWithSeatsDto {
    private String flightName;
    private String flightDescription;
    private List<SeatDto> seats;
}
