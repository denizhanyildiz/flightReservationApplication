package com.flight.ReservationService.Dummy;

import com.flight.ReservationService.Entity.Airport;
import com.flight.ReservationService.Entity.Enum.Category;
import com.flight.ReservationService.Entity.Enum.Country;
import com.flight.ReservationService.Entity.Enum.Currency;
import com.flight.ReservationService.Entity.Enum.State;
import com.flight.ReservationService.Entity.Money;
import com.flight.ReservationService.Entity.Plane;
import com.flight.ReservationService.Entity.Seat;
import com.flight.ReservationService.Service.AirportService;
import com.flight.ReservationService.Service.MoneyService;
import com.flight.ReservationService.Service.PlaneService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DummyDataInitializer {

    private final AirportService airportService;
    private final PlaneService planeService;
    private final MoneyService moneyService;
    private static final Logger logger = LogManager.getLogger(DummyDataInitializer.class);

    public DummyDataInitializer(AirportService airportService, PlaneService planeService, MoneyService moneyService) {
        this.airportService = airportService;
        this.planeService = planeService;
        this.moneyService = moneyService;
    }

    @PostConstruct
    public void initializeDummyData() {
        if (airportService.findAll().isEmpty()) {
            List<Airport> airports = List.of(
                    createAirport("Istanbul Airport", "IST", "Istanbul, Turkey", Country.TURKEY),
                    createAirport("Sabiha Gökçen International Airport", "SAW", "Istanbul, Turkey", Country.TURKEY),
                    createAirport("Ankara Esenboğa Airport", "ESB", "Ankara, Turkey", Country.TURKEY),
                    createAirport("Antalya Airport", "AYT", "Antalya, Turkey", Country.TURKEY),
                    createAirport("Izmir Adnan Menderes Airport", "ADB", "Izmir, Turkey", Country.TURKEY),
                    createAirport("Trabzon Airport", "TZX", "Trabzon, Turkey", Country.TURKEY),
                    createAirport("Dalaman Airport", "DLM", "Mugla, Turkey", Country.TURKEY),
                    createAirport("Gaziantep Airport", "GZT", "Gaziantep, Turkey", Country.TURKEY),
                    createAirport("Kayseri Erkilet Airport", "ASR", "Kayseri, Turkey", Country.TURKEY),
                    createAirport("Bodrum Milas Airport", "BJV", "Bodrum, Turkey", Country.TURKEY)
            );

            airports.forEach(airportService::save);
            logger.info("Dummy airport data initialized.");
        }

        if (planeService.findAll().isEmpty()) {
            Money economyMoney = createMoney(Currency.TRY, "100.00");
            Money businessMoney = createMoney(Currency.TRY, "500.00");

            List<Plane> planes = List.of(
                    createPlane("Airbus A320", "A320","A320-1", economyMoney, businessMoney, 5, 3, 200L),
                    createPlane("Boeing 737 Max", "737 Max","737-1", economyMoney, businessMoney, 6, 4, 300L),
                    createPlane("Bombardier CRJ900", "CRJ900","CRJ-1", economyMoney, businessMoney, 4, 2, 400L),
                    createPlane("Cessna Citation Latitude", "Citation Latitude","CCL-1", economyMoney, businessMoney, 3, 1, 500L),
                    createPlane("Gulfstream G650", "G650","G650-1", economyMoney, businessMoney, 3, 3, 600L)
            );

            planes.forEach(planeService::save);

            logger.info("Dummy plane data initialized.");
        }
    }

    private List<Seat> generateSeats(Plane plane, Money money, Category category, int count) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            seats.add(new Seat(category.getShortening() + i, category, plane, money, State.PURCHASABLE));
        }
        return seats;
    }

    private Money createMoney(Currency currency, String amount) {
        return moneyService.save(new Money(currency, new BigDecimal(amount)));
    }

    private Plane createPlane(String name, String model, String code, Money economyMoney, Money businessMoney, int economySeats, int businessSeats, Long seatSize) {
        Plane plane = new Plane(name, model, code, seatSize);
        List<Seat> seats = new ArrayList<>();
        seats.addAll(generateSeats(plane, economyMoney, Category.ECONOMY, economySeats));
        seats.addAll(generateSeats(plane, businessMoney, Category.BUSINESS, businessSeats));
        plane.setSeats(seats);
        return plane;
    }

    private Airport createAirport(String name, String code, String location, Country country) {
        return new Airport(name, code, location, country);
    }
}
