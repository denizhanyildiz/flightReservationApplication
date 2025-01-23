package com.flight.ReservationService.Service;

import com.flight.ReservationService.CustomException.SeatAlreadySoldException;
import com.flight.ReservationService.CustomException.SeatNotFoundException;
import com.flight.ReservationService.Dto.Request.UpdateSeatRequestDto;
import com.flight.ReservationService.Entity.Enum.State;
import com.flight.ReservationService.Entity.Enum.Status;
import com.flight.ReservationService.Entity.Money;
import com.flight.ReservationService.Entity.Plane;
import com.flight.ReservationService.Entity.Seat;
import com.flight.ReservationService.Service.Impl.SeatRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public Seat updateSeat(Plane plane, String seatNumber, UpdateSeatRequestDto dto) {
        Seat seat = findByNumberAndPlain(seatNumber, plane);
        if (seat.getState().equals(State.SOLD)) {
            throw new SeatAlreadySoldException("Seat has been sold can not be update.");
        }

        Optional.ofNullable(dto.getCategory()).ifPresent(seat::setCategory);
        Optional.ofNullable(dto.getMoney()).ifPresent(money -> {
            Money updatedMoney = new Money(money.getCurrency(), money.getAmount());
            Money savedMoney = moneyService.save(updatedMoney);
            seat.setMoney(savedMoney);
        });
        Optional.ofNullable(dto.getState()).ifPresent(seat::setState);
        return save(seat);
    }

    public void makeSeatAvailable(Seat seat) {
        seat.setStatus(Status.ACTIVE);
        seat.setState(State.PURCHASABLE);
        update(seat);
    }
}
