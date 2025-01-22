package com.flight.ReservationService.CustomException;

public class AirportNotFoundException extends RuntimeException {
    public AirportNotFoundException(String message) {
        super(message);
    }
}
