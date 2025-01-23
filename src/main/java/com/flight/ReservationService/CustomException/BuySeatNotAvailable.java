package com.flight.ReservationService.CustomException;

public class BuySeatNotAvailable extends RuntimeException{
    public BuySeatNotAvailable(String message) {
        super(message);
    }
}
