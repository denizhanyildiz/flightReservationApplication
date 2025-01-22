package com.flight.ReservationService.Service.Impl;

import com.flight.ReservationService.Entity.Plane;
import com.flight.ReservationService.Service.PlaneRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlaneService {

    private final PlaneRepository planeRepository;

    public PlaneService(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    public List<Plane> findAll() {
        return planeRepository.findAll();
    }

    public Plane findById(Long id) {
        return planeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plane not found with id: " + id));
    }

    public Plane save(Plane plane) {
        return planeRepository.save(plane);
    }

    public void deleteById(Long id) {
        planeRepository.deleteById(id);
    }
}
