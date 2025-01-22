package com.flight.ReservationService.Service;

import com.flight.ReservationService.CustomException.PlaneExceedsMaximumSeat;
import com.flight.ReservationService.CustomException.PlaneNotFoundException;
import com.flight.ReservationService.Dto.AddSeatRequestDto;
import com.flight.ReservationService.Dto.UpdateSeatRequestDto;
import com.flight.ReservationService.Entity.Enum.Category;
import com.flight.ReservationService.Entity.Enum.State;
import com.flight.ReservationService.Entity.Enum.Status;
import com.flight.ReservationService.Entity.Plane;
import com.flight.ReservationService.Entity.Seat;
import com.flight.ReservationService.Service.Impl.PlaneRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlaneService {

    private final PlaneRepository planeRepository;
    private final SeatService seatService;

    public PlaneService(PlaneRepository planeRepository, SeatService seatService) {
        this.planeRepository = planeRepository;
        this.seatService = seatService;
    }

    public List<Plane> findAll() {
        return planeRepository.findAll();
    }

    public Plane findByCode(String code){
        return planeRepository.findByCodeAndStatus(code, Status.ACTIVE)
                .orElseThrow(() -> new PlaneNotFoundException("Plane not found with code: " + code));
    }

    public Plane findById(Long id) {
        return planeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plane not found with id: " + id));
    }

    public Plane save(Plane plane) {
        plane.setStatus(Status.ACTIVE);
        plane.getSeats().forEach(s -> s.setStatus(Status.ACTIVE));
        plane.getSeats().forEach(s -> s.setState(State.PURCHASABLE));
        return planeRepository.save(plane);
    }

    public Plane update(Plane plane) {
        return planeRepository.save(plane);
    }

    public void deleteById(Long id) {
        Plane deleted = findById(id);
        deleted.setStatus(Status.DELETED);
        planeRepository.save(deleted);
    }

    public Plane addSeatsToPlane(Plane plane, List<AddSeatRequestDto> addSeatRequestDtos) {
        if (plane.getSeats().size() + addSeatRequestDtos.size() > plane.getMaxSeatSize()) {
            throw new PlaneExceedsMaximumSeat("Cannot add seats. Exceeds maximum seat capacity for this plane.");
        }

        List<Seat> existingSeats = plane.getSeats();
        Map<Category, Integer> seatCountByCategory = new HashMap<>();
        for (Seat seat : existingSeats) {
            String seatNumber = seat.getNumber();
            String[] parts = seatNumber.split("-");
            if (parts.length == 2) {
                Category category = seat.getCategory();
                int number = Integer.parseInt(parts[1]);
                seatCountByCategory.put(category, Math.max(seatCountByCategory.getOrDefault(category, 0), number));
            }
        }

        List<Seat> newSeats = new ArrayList<>();
        for (AddSeatRequestDto dto : addSeatRequestDtos) {
            Category category = dto.getCategory();
            int nextNumber = seatCountByCategory.getOrDefault(category, 0) + 1;
            String newSeatNumber = generateSeatNumber(category, nextNumber);

            Seat newSeat = new Seat(
                    newSeatNumber,
                    category,
                    plane,
                    dto.getMoney(),
                    State.PURCHASABLE
            );

            newSeats.add(newSeat);
            seatCountByCategory.put(category, nextNumber);
        }

        seatService.saveAll(newSeats);
        plane.getSeats().addAll(newSeats);
        return update(plane);
    }

    public Plane removeSeat(Plane plane, String seatNumber) {
        Seat seat = seatService.findByNumberAndPlain(seatNumber, plane);
        seat.setState(State.UN_PURCHASABLE);
        seatService.update(seat);
        return findByCode(plane.getCode());
    }

    public Plane updateSeat(Plane plane, UpdateSeatRequestDto updateSeatRequestDto) {
        seatService.updateSeat(plane, updateSeatRequestDto);
        return findByCode(plane.getCode());
    }

    private String generateSeatNumber(Category category, int number) {
        return category.getShortening() + "-" + number;
    }
}
