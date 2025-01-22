package com.flight.ReservationService.Service.Impl;

import com.flight.ReservationService.Entity.Enum.Status;
import com.flight.ReservationService.Entity.Flight;
import com.flight.ReservationService.Entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    boolean existsByPlaneAndPlannedTimeAndStatus(Plane plane, LocalDateTime plannedTime, Status status);
    Optional<Flight> findByIdAndStatus(Long id, Status status);
    Optional<Flight> findByFlightNumberAndStatus(String flightNumber, Status status);
    Optional<List<Flight>> findByStatus(Status status);

}
