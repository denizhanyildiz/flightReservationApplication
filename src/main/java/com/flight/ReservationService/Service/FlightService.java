package com.flight.ReservationService.Service;

import com.flight.ReservationService.CustomException.FlightNotFoundException;
import com.flight.ReservationService.CustomException.FlightPlannedExistsWithSamePlane;
import com.flight.ReservationService.Dto.Request.CreateFlightRequestDto;
import com.flight.ReservationService.Dto.Request.AddSeatRequestDto;
import com.flight.ReservationService.Dto.Request.UpdateFlightRequestDto;
import com.flight.ReservationService.Dto.Request.UpdateSeatRequestDto;
import com.flight.ReservationService.Dto.Response.FlightWithSeatsDto;
import com.flight.ReservationService.Entity.*;
import com.flight.ReservationService.Entity.Enum.Situation;
import com.flight.ReservationService.Entity.Enum.Status;
import com.flight.ReservationService.Service.Impl.FlightRepository;
import com.flight.ReservationService.Utils.FlightDtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportService airportService;
    private final PlaneService planeService;

    public FlightService(FlightRepository flightRepository, AirportService airportService, PlaneService planeService) {
        this.flightRepository = flightRepository;
        this.airportService = airportService;
        this.planeService = planeService;
    }

    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    public Flight findById(Long id) {
        return flightRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with id: " + id + " or the flight is not active."));
    }

    public Flight save(Flight flight) {
        flight.setStatus(Status.ACTIVE);
        return flightRepository.save(flight);
    }

    public void deleteById(Long id) {
        Flight deleted = findById(id);
        deleted.setStatus(Status.DELETED);
        flightRepository.save(deleted);
    }

    public List<Flight> findByStatus(Status status) {
        return flightRepository.findByStatus(status)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with status: " + status + " or the flight is not active."));
    }

    public Flight update(Flight flight) {
        return flightRepository.save(flight);
    }

    public Flight findByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumberAndStatus(flightNumber, Status.ACTIVE)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with number: " + flightNumber));
    }

    public Flight createFlight(CreateFlightRequestDto createFlightRequestDto) {
        Plane plane = planeService.findByCode(createFlightRequestDto.getPlaneCode());

        boolean flightExists = flightRepository.existsByPlaneAndPlannedTimeAndStatus(plane, createFlightRequestDto.getPlannedTime(), Status.ACTIVE);
        if (flightExists) {
            throw new FlightPlannedExistsWithSamePlane("A flight with the same planned time already exists for this plane.");
        }

        Airport takeoffAirport = airportService.findByCode(createFlightRequestDto.getTakeoffAirportCode());
        Airport arrivalAirport = airportService.findByCode(createFlightRequestDto.getArrivalAirportCode());

        Flight flight = new Flight();
        flight.setFlightName(createFlightRequestDto.getFlightName());
        flight.setFlightDescription(createFlightRequestDto.getFlightDescription());
        flight.setSituation(Situation.ON_TIME);
        flight.setPlannedTime(createFlightRequestDto.getPlannedTime());
        flight.setTakeoffAirport(takeoffAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setFlightNumber(UUID.randomUUID().toString());
        flight.setPlane(plane);

        return save(flight);
    }

    public Flight removeFlight(String flightNumber){
        Flight flight = flightRepository.findByFlightNumberAndStatus(flightNumber, Status.ACTIVE)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with number: " + flightNumber));
        flight.setSituation(Situation.CANCELLED);
        return update(flight);
    }

    public Flight updateFlight(String flightNumber, UpdateFlightRequestDto flightRequestDto) {
        Flight flight = flightRepository.findByFlightNumberAndStatus(flightNumber, Status.ACTIVE)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with number: " + flightNumber));

        Optional.ofNullable(flightRequestDto.getFlightName()).ifPresent(flight::setFlightName);
        Optional.ofNullable(flightRequestDto.getFlightDescription()).ifPresent(flight::setFlightDescription);
        Optional.ofNullable(flightRequestDto.getSituation()).ifPresent(flight::setSituation);

        Optional.ofNullable(flightRequestDto.getPlannedTime()).ifPresent(plannedTime -> {
            if (plannedTime.isBefore(flight.getPlannedTime())) {
                flight.setSituation(Situation.ADVANCE);
            } else if (plannedTime.isAfter(flight.getPlannedTime())) {
                flight.setSituation(Situation.DELAYED);
            }
            flight.setPlannedTime(plannedTime);
        });

        Optional.ofNullable(flightRequestDto.getTakeoffAirportCode()).ifPresent(code -> {
            Airport takeoffAirport = airportService.findByCode(code);
            flight.setTakeoffAirport(takeoffAirport);
        });

        Optional.ofNullable(flightRequestDto.getArrivalAirportCode()).ifPresent(code -> {
            Airport arrivalAirport = airportService.findByCode(code);
            flight.setArrivalAirport(arrivalAirport);
        });

        Optional.ofNullable(flightRequestDto.getPlaneCode()).ifPresent(code -> {
            Plane plane = planeService.findByCode(code);
            flight.setPlane(plane);
        });

        return update(flight);
    }

    public Plane addSeat(String flightNumber, List<AddSeatRequestDto> addSeatRequestDtos) {
        Flight flight = flightRepository.findByFlightNumberAndStatus(flightNumber, Status.ACTIVE)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with number: " + flightNumber));

        return planeService.addSeatsToPlane(flight.getPlane(), addSeatRequestDtos);
    }

    public Plane removeSeat(String flightNumber, String seatNumber) {
        Flight flight = flightRepository.findByFlightNumberAndStatus(flightNumber, Status.ACTIVE)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with number: " + flightNumber));
        return planeService.removeSeat(flight.getPlane(), seatNumber);
    }

    public Plane updateSeat(String flightNumber, String seatNumber, UpdateSeatRequestDto seatRequestDto) {
        Flight flight = flightRepository.findByFlightNumberAndStatus(flightNumber, Status.ACTIVE)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with number: " + flightNumber));
        return planeService.updateSeat(flight.getPlane(), seatNumber, seatRequestDto);
    }

    public FlightWithSeatsDto getFlightWithSeats(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumberAndStatus(flightNumber, Status.ACTIVE)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with number: " + flightNumber));

        return FlightDtoMapper.mapToFlightWithSeatsDto(flight);
    }
}
