package com.flight.ReservationService.Entity;

import com.flight.ReservationService.Entity.Enum.Currency;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Money extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;
    @Column(nullable = false)
    private BigDecimal amount;
    @OneToMany(mappedBy = "money", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;
}
