package com.flight.ReservationService.Dto.Request;

import com.flight.ReservationService.Entity.Enum.Category;
import com.flight.ReservationService.Entity.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSeatRequestDto {
    private String number;
    private Category category;
    private Money money;
}
