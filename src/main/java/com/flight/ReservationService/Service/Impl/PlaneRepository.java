package com.flight.ReservationService.Service.Impl;

import com.flight.ReservationService.Entity.Enum.Status;
import com.flight.ReservationService.Entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaneRepository extends JpaRepository<Plane, Long> {
    Optional<Plane> findByCodeAndStatus(String code, Status status);
}
