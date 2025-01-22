package com.flight.ReservationService.CustomException;

public class SeatNotFoundException extends RuntimeException {
    public SeatNotFoundException(String message) {
        super(message);
    }
}
