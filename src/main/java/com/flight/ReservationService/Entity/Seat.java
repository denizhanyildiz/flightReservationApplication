package com.flight.ReservationService.Entity;

import com.flight.ReservationService.Entity.Enum.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat extends BaseEntity {
    private String number;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plane_id", nullable = false)
    private Plane plane;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "money_id", nullable = false)
    private Money money;
}
