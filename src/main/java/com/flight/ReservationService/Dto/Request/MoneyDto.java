package com.flight.ReservationService.Dto.Request;

import com.flight.ReservationService.Entity.Enum.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MoneyDto {
    private Currency currency;
    private BigDecimal amount;
}
