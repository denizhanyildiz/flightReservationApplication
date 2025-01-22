package com.flight.ReservationService.CustomException;

public class SeatAlreadySoldException extends IllegalStateException {
    public SeatAlreadySoldException(String s) {
        super(s);
    }
}
