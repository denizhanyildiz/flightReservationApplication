package com.flight.ReservationService.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plane extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false, unique = true)
    private String code;
    @OneToMany(mappedBy = "plane", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "plane-seat")
    private List<Seat> seats;
    @OneToMany(mappedBy = "plane", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "plane-flight")
    private List<Flight> flights;
    @Column(nullable = false)
    private Long maxSeatSize;

    public Plane(String name, String model, String code, Long maxSeatSize) {
        this.name = name;
        this.model = model;
        this.code = code;
        this.maxSeatSize = maxSeatSize;
    }
}
