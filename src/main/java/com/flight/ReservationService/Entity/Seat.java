package com.flight.ReservationService.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.flight.ReservationService.Entity.Enum.Category;
import com.flight.ReservationService.Entity.Enum.State;
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
    @JsonBackReference(value = "plane-seat")
    private Plane plane;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "money_id", nullable = false)
    @JsonBackReference(value = "money-seat")
    private Money money;
    @Enumerated(EnumType.STRING)
    private State state;
}
