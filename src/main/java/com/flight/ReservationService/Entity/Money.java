package com.flight.ReservationService.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.flight.ReservationService.Entity.Enum.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Money extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;
    @Column(nullable = false)
    private BigDecimal amount;
    @OneToMany(mappedBy = "money", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "money-seat")
    private List<Seat> seats;

    public Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }
}
