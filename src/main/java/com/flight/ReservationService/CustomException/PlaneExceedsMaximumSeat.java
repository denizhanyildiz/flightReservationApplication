package com.flight.ReservationService.CustomException;

public class PlaneExceedsMaximumSeat extends IllegalArgumentException {
    public PlaneExceedsMaximumSeat(String s) {
        super(s);
    }
}
