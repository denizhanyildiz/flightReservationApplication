package com.flight.ReservationService.Service;

import com.flight.ReservationService.Entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaneRepository extends JpaRepository<Plane, Long> {
}
