package com.flight.ReservationService.Service.Impl;

import com.flight.ReservationService.Entity.Money;
import com.flight.ReservationService.Service.MoneyRepository;
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
        return moneyRepository.save(money);
    }

    public void deleteById(Long id) {
        moneyRepository.deleteById(id);
    }
}
