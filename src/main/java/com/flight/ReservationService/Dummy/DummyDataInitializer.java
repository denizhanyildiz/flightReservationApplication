package com.flight.ReservationService.Dummy;

import com.flight.ReservationService.Entity.Airport;
import com.flight.ReservationService.Entity.Enum.Country;
import com.flight.ReservationService.Service.Impl.AirportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DummyDataInitializer {

    private final AirportService airportService;
    private static final Logger logger = LogManager.getLogger(DummyDataInitializer.class);

    public DummyDataInitializer(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostConstruct
    public void initializeDummyData() {
        if (airportService.findAll().isEmpty()) {
            airportService.save(new Airport("Istanbul Airport", "IST", "Istanbul, Turkey", Country.TURKEY));
            airportService.save(new Airport("Sabiha Gökçen International Airport", "SAW", "Istanbul, Turkey", Country.TURKEY));
            airportService.save(new Airport("Ankara Esenboğa Airport", "ESB", "Ankara, Turkey", Country.TURKEY));
            airportService.save(new Airport("Antalya Airport", "AYT", "Antalya, Turkey", Country.TURKEY));
            airportService.save(new Airport("Izmir Adnan Menderes Airport", "ADB", "Izmir, Turkey", Country.TURKEY));
            airportService.save(new Airport("Trabzon Airport", "TZX", "Trabzon, Turkey", Country.TURKEY));
            airportService.save(new Airport("Dalaman Airport", "DLM", "Mugla, Turkey", Country.TURKEY));
            airportService.save(new Airport("Gaziantep Airport", "GZT", "Gaziantep, Turkey", Country.TURKEY));
            airportService.save(new Airport("Kayseri Erkilet Airport", "ASR", "Kayseri, Turkey", Country.TURKEY));
            airportService.save(new Airport("Bodrum Milas Airport", "BJV", "Bodrum, Turkey", Country.TURKEY));

            logger.info("Dummy airport data initialized.");
        }
    }
}
