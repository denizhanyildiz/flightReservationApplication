package com.flight.ReservationService.CustomException;

public class SeatCanNotBeRemove extends RuntimeException {
    public SeatCanNotBeRemove(String message) {
        super(message);
    }
}
