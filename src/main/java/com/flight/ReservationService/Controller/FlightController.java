package com.flight.ReservationService.Controller;

import com.flight.ReservationService.Dto.Request.CreateFlightRequestDto;
import com.flight.ReservationService.Dto.Request.AddSeatRequestDto;
import com.flight.ReservationService.Dto.Request.UpdateFlightRequestDto;
import com.flight.ReservationService.Dto.Request.UpdateSeatRequestDto;
import com.flight.ReservationService.Dto.Response.FlightWithSeatsDto;
import com.flight.ReservationService.Entity.Flight;
import com.flight.ReservationService.Entity.Plane;
import com.flight.ReservationService.Service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping(path = "/addFlight")
    public ResponseEntity<Flight> addFlight(@RequestBody CreateFlightRequestDto createFlightRequestDto) {
        Flight flight = flightService.createFlight(createFlightRequestDto);
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }

    @PutMapping(path = "/removeFlight/{flightNumber}")
    public ResponseEntity<Flight> removeFlight(@PathVariable String flightNumber) {
        Flight removedFlight = flightService.removeFlight(flightNumber);
        return new ResponseEntity<>(removedFlight, HttpStatus.OK);
    }

    @PutMapping(path = "/updateFlight/{flightNumber}")
    public ResponseEntity<Flight> updateFlight(@PathVariable String flightNumber, @RequestBody UpdateFlightRequestDto flightRequestDto) {
        Flight updatedFlight = flightService.updateFlight(flightNumber, flightRequestDto);
        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }

    @PutMapping(path = "/addSeat/{flightNumber}")
    public ResponseEntity<Plane> addSeat(@PathVariable String flightNumber, @RequestBody List<AddSeatRequestDto> addSeatRequestDtos) {
        Plane updatedFlight = flightService.addSeat(flightNumber, addSeatRequestDtos);
        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }

    @PutMapping(path = "/removeSeat/{flightNumber}/{seatNumber}")
    public ResponseEntity<Plane> removeSeat(@PathVariable String flightNumber, @PathVariable String seatNumber) {
        Plane updatedFlight = flightService.removeSeat(flightNumber, seatNumber);
        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }

    @PutMapping(path = "/updateSeat/{flightNumber}")
    public ResponseEntity<Plane> updateSeat(@PathVariable String flightNumber, @RequestBody UpdateSeatRequestDto seatRequestDto) {
        Plane updatedFlight = flightService.updateSeat(flightNumber, seatRequestDto);
        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }

    @GetMapping("/detail/{flightNumber}")
    public ResponseEntity<FlightWithSeatsDto> getFlightWithSeats(@PathVariable String flightNumber) {
        FlightWithSeatsDto flight = flightService.getFlightWithSeats(flightNumber);
        return ResponseEntity.ok(flight);
    }

}
