package com.flight.ReservationService.Service.Impl;

import com.flight.ReservationService.Entity.Seat;
import com.flight.ReservationService.Service.SeatRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seat> findAll() {
        return seatRepository.findAll();
    }

    public Seat findById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found with id: " + id));
    }

    public Seat save(Seat seat) {
        return seatRepository.save(seat);
    }

    public void deleteById(Long id) {
        seatRepository.deleteById(id);
    }
}
