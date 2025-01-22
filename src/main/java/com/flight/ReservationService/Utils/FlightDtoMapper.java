package com.flight.ReservationService.Utils;

import com.flight.ReservationService.Dto.Response.FlightWithSeatsDto;
import com.flight.ReservationService.Dto.Response.MoneyDto;
import com.flight.ReservationService.Dto.Response.SeatDto;
import com.flight.ReservationService.Entity.Flight;
import com.flight.ReservationService.Entity.Money;
import com.flight.ReservationService.Entity.Seat;

import java.util.List;
import java.util.stream.Collectors;

public class FlightDtoMapper {

    public FlightDtoMapper() {
    }

    public static FlightWithSeatsDto mapToFlightWithSeatsDto(Flight flight) {
        List<SeatDto> seatDtos = flight.getPlane().getSeats().stream()
                .map(FlightDtoMapper::mapToSeatDto) // Use static method reference
                .collect(Collectors.toList());

        return new FlightWithSeatsDto(
                flight.getFlightName(),
                flight.getFlightDescription(),
                seatDtos
        );
    }

    public static SeatDto mapToSeatDto(Seat seat) {
        return new SeatDto(
                seat.getNumber(),
                seat.getCategory(),
                seat.getState(),
                mapToMoneyDto(seat.getMoney()) // Use static method
        );
    }

    public static MoneyDto mapToMoneyDto(Money money) {
        return new MoneyDto(
                money.getCurrency(),
                money.getAmount()
        );
    }
}
