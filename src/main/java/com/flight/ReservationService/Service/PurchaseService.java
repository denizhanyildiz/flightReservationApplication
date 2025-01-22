package com.flight.ReservationService.Service;

import com.flight.ReservationService.CustomException.SeatAlreadySoldException;
import com.flight.ReservationService.Entity.Enum.State;
import com.flight.ReservationService.Entity.Seat;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PurchaseService {
    private final SeatService seatService;

    public PurchaseService(SeatService seatService) {
        this.seatService = seatService;
    }

    @Transactional
    public Seat buySeat(String seatNumber) {
        Seat seat = seatService.findByNumber(seatNumber);

        if (seat.getState() != State.PURCHASABLE) {
            throw new SeatAlreadySoldException("Seat is not available for purchase");
        }
        seat.setState(State.SOLD);
        return seatService.save(seat);
    }
}
