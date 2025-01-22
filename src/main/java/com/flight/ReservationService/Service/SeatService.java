package com.flight.ReservationService.Service;

import com.flight.ReservationService.CustomException.SeatNotFoundException;
import com.flight.ReservationService.Dto.Request.UpdateSeatRequestDto;
import com.flight.ReservationService.Entity.Enum.Status;
import com.flight.ReservationService.Entity.Money;
import com.flight.ReservationService.Entity.Plane;
import com.flight.ReservationService.Entity.Seat;
import com.flight.ReservationService.Service.Impl.SeatRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final MoneyService moneyService;

    public SeatService(SeatRepository seatRepository, MoneyService moneyService) {
        this.seatRepository = seatRepository;
        this.moneyService = moneyService;
    }

    public List<Seat> findAll() {
        return seatRepository.findAll();
    }

    public Seat findById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found with id: " + id));
    }

    public Seat save(Seat seat) {
        seat.setStatus(Status.ACTIVE);
        seat.getMoney().setStatus(Status.ACTIVE);
        return seatRepository.save(seat);
    }

    public Seat update(Seat seat) {
        return seatRepository.save(seat);
    }

    public void saveAll(List<Seat> seats) {
        seats.forEach(s -> s.setStatus(Status.ACTIVE));
        seats.forEach(s -> moneyService.save(s.getMoney()));
        seatRepository.saveAll(seats);
    }

    public void deleteById(Long id) {
        Seat deleted = findById(id);
        deleted.setStatus(Status.DELETED);
        deleted.getMoney().setStatus(Status.DELETED);
        seatRepository.save(deleted);
    }

    public Seat findByNumberAndPlain(String seatNumber, Plane plane){
        return seatRepository.findByNumberAndPlaneAndStatus(seatNumber, plane, Status.ACTIVE)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found with number: " + seatNumber + " in this plane"));
    }

    public Seat findByNumber(String seatNumber){
        return seatRepository.findByNumberAndStatus(seatNumber, Status.ACTIVE)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found with number: " + seatNumber + " in this plane"));
    }

    public Seat updateSeat(Plane plane, UpdateSeatRequestDto dto) {
        Seat seat = findByNumberAndPlain(dto.getSeatNumber(), plane);

        if (dto.getCategory() != null) {
            seat.setCategory(dto.getCategory());
        }

        if (dto.getMoney() != null) {
            Money updatedMoney = new Money(dto.getMoney().getCurrency(), dto.getMoney().getAmount());
            Money savedMoney = moneyService.save(updatedMoney);
            seat.setMoney(savedMoney);
        }

        if (dto.getState() != null) {
            seat.setState(dto.getState());
        }

        return seatRepository.save(seat);
    }
}
