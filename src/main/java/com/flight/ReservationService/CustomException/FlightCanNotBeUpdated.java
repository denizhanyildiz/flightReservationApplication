package com.flight.ReservationService.CustomException;

public class FlightCanNotBeUpdated extends RuntimeException {
    public FlightCanNotBeUpdated(String message) {
        super(message);
    }
}
