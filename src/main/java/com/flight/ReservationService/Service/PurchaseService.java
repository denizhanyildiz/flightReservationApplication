package com.flight.ReservationService.Service;

import com.flight.ReservationService.CustomException.SeatAlreadySoldException;
import com.flight.ReservationService.Entity.Enum.State;
import com.flight.ReservationService.Entity.Flight;
import com.flight.ReservationService.Entity.Seat;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import javax.persistence.PessimisticLockException;
import javax.transaction.Transactional;

@Service
public class PurchaseService {
    private final SeatService seatService;
    private final FlightService flightService;

    public PurchaseService(SeatService seatService, FlightService flightService) {
        this.seatService = seatService;
        this.flightService = flightService;
    }

    @Transactional
    public Seat buySeat(String seatNumber, String flightNumber) {
        try {
            Flight flight = flightService.findByFlightNumber(flightNumber);
            Seat seat = seatService.findByNumberAndPlain(seatNumber, flight.getPlane());

            if (seat.getState() != State.PURCHASABLE) {
                throw new SeatAlreadySoldException("Seat is not available for purchase");
            }
            seat.setState(State.SOLD);
            return seatService.save(seat);
        } catch (OptimisticLockingFailureException | PessimisticLockException e ) {
            throw new SeatAlreadySoldException("Seat is not available for purchase");
        }

    }
}
