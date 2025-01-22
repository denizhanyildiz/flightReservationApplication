package com.flight.ReservationService.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.flight.ReservationService.CustomException.InvalidFlightException;
import com.flight.ReservationService.Entity.Enum.Situation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Situation situation;
    @Column(nullable = false)
    private LocalDateTime plannedTime;
    @Column(nullable = false, unique = true)
    private String flightNumber;
    private String flightName;
    @Column(columnDefinition = "TEXT")
    private String flightDescription;
    @ManyToOne
    @JoinColumn(name = "plane_id", nullable = false)
    @JsonBackReference(value = "plane-flight")
    private Plane plane;
    @ManyToOne
    @JoinColumn(name = "takeoff_airport_id", nullable = false)
    private Airport takeoffAirport;
    @ManyToOne
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    private Airport arrivalAirport;

    @PrePersist
    @PreUpdate
    private void validateAirports() {
        if (takeoffAirport != null && takeoffAirport.equals(arrivalAirport)) {
            throw new InvalidFlightException("Takeoff airport and arrival airport cannot be the same.");
        }
    }
}
