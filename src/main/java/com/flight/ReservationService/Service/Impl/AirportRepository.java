package com.flight.ReservationService.Service.Impl;

import com.flight.ReservationService.Entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByCode(String code);
}
