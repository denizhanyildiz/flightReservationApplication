package com.flight.ReservationService.Service;

import com.flight.ReservationService.Entity.Airport;
import com.flight.ReservationService.Entity.Enum.Status;
import com.flight.ReservationService.Service.Impl.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    public Airport findById(Long id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airport not found with id: " + id));
    }

    public Airport findByCode(String code) {
        return airportRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Airport not found with code: " + code));
    }

    public Airport save(Airport airport) {
        airport.setStatus(Status.ACTIVE);
        return airportRepository.save(airport);
    }

    public void deleteById(Long id) {
        Airport deleted = findById(id);
        deleted.setStatus(Status.DELETED);
        airportRepository.save(deleted);
    }


}
