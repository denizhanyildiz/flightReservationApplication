package com.flight.ReservationService.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plane extends BaseEntity{
    private String name;
    private String model;
    @OneToMany(mappedBy = "plane", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;
    @OneToOne(mappedBy = "plane", cascade = CascadeType.ALL, orphanRemoval = true)
    private Flight flight;
}
