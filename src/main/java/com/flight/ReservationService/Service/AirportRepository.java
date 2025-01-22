package com.flight.ReservationService.Service;

import com.flight.ReservationService.Entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
