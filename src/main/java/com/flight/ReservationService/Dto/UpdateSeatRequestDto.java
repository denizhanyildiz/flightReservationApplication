package com.flight.ReservationService.Dto;

import com.flight.ReservationService.Entity.Enum.Category;
import com.flight.ReservationService.Entity.Enum.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSeatRequestDto {
    private String seatNumber;
    private Category category;
    private MoneyDto money;
    private State state;
}
