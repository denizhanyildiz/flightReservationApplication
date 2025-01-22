package com.flight.ReservationService.Service.Impl;

import com.flight.ReservationService.Entity.Enum.Status;
import com.flight.ReservationService.Entity.Plane;
import com.flight.ReservationService.Entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByNumberAndPlaneAndStatus(String seatNumber, Plane plane, Status status);
    Optional<Seat> findByNumberAndStatus(String seatNumber, Status status);
}
