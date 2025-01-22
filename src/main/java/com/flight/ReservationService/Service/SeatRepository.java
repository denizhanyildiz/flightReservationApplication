package com.flight.ReservationService.Service;

import com.flight.ReservationService.Entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
