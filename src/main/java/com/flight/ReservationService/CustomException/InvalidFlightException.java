package com.flight.ReservationService.CustomException;

public class InvalidFlightException extends RuntimeException{
    public InvalidFlightException(String message) {
        super(message);
    }
}
