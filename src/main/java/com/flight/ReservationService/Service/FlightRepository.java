package com.flight.ReservationService.Service;

import com.flight.ReservationService.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
