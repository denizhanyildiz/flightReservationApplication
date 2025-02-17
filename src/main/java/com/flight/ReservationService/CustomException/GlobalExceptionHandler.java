package com.flight.ReservationService.CustomException;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFlightException.class)
    public ResponseEntity<String> handleInvalidFlightException(InvalidFlightException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FlightPlannedExistsWithSamePlane.class)
    public ResponseEntity<String> handleFlightPlannedExistsWithSamePlaneException(FlightPlannedExistsWithSamePlane ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlaneNotFoundException.class)
    public ResponseEntity<String> handlePlaneNotFoundException(PlaneNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<String> handleFlightNotFoundException(FlightNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AirportNotFoundException.class)
    public ResponseEntity<String> handleAirportNotFoundException(AirportNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlaneExceedsMaximumSeat.class)
    public ResponseEntity<String> handlePlaneExceedsMaximumSeat(PlaneExceedsMaximumSeat ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity<String> handleSeatNotFoundException(SeatNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SeatAlreadySoldException.class)
    public ResponseEntity<String> handleSeatAlreadySoldException(SeatAlreadySoldException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BuySeatNotAvailable.class)
    public ResponseEntity<String> handleBuySeatNotAvailable(BuySeatNotAvailable ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FlightCanNotBeUpdated.class)
    public ResponseEntity<String> handleFlightCanNotBeUpdated(FlightCanNotBeUpdated ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SeatCanNotBeRemove.class)
    public ResponseEntity<String> handleSeatCanNotBeRemove(SeatCanNotBeRemove ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
