package com.flight.ReservationService.Controller;

import com.flight.ReservationService.Entity.Seat;
import com.flight.ReservationService.Service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/buySeat/{seatNumber}/{flightNumber}")
    public ResponseEntity<Seat> buySeat(@PathVariable String seatNumber, @PathVariable String flightNumber) {
        Seat purchasedSeat = purchaseService.buySeat(seatNumber, flightNumber);
        return ResponseEntity.ok(purchasedSeat);
    }

}
