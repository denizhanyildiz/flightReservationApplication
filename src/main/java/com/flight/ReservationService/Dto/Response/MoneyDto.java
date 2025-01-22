package com.flight.ReservationService.Dto.Response;

import com.flight.ReservationService.Entity.Enum.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoneyDto {
    private Currency currency;
    private BigDecimal amount;
}
