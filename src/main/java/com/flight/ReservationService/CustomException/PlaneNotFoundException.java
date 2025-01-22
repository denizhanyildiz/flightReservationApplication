package com.flight.ReservationService.CustomException;

public class PlaneNotFoundException extends RuntimeException {
    public PlaneNotFoundException(String message) {
        super(message);
    }
}
