package com.flight.ReservationService.Service;

import com.flight.ReservationService.Entity.Enum.Status;
import com.flight.ReservationService.Entity.Money;
import com.flight.ReservationService.Service.Impl.MoneyRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MoneyService {

    private final MoneyRepository moneyRepository;

    public MoneyService(MoneyRepository moneyRepository) {
        this.moneyRepository = moneyRepository;
    }

    public List<Money> findAll() {
        return moneyRepository.findAll();
    }

    public Money findById(Long id) {
        return moneyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Money not found with id: " + id));
    }

    public Money save(Money money) {
        money.setStatus(Status.ACTIVE);
        return moneyRepository.save(money);
    }

    public void deleteById(Long id) {
        Money deleted = findById(id);
        deleted.setStatus(Status.DELETED);
        moneyRepository.save(deleted);
    }
}
