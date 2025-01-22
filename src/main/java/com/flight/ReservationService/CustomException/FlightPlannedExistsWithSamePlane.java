package com.flight.ReservationService.CustomException;

public class FlightPlannedExistsWithSamePlane extends RuntimeException {
    public FlightPlannedExistsWithSamePlane(String message) {
        super(message);
    }
}
