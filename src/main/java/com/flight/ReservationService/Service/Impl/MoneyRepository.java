package com.flight.ReservationService.Service.Impl;

import com.flight.ReservationService.Entity.Money;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyRepository extends JpaRepository<Money, Long> {
}
