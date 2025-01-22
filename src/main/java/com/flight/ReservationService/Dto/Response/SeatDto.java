package com.flight.ReservationService.Dto.Response;

import com.flight.ReservationService.Entity.Enum.Category;
import com.flight.ReservationService.Entity.Enum.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {
    private String number;
    private Category category;
    private State state;
    private MoneyDto money;
}
