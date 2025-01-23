package com.flight.ReservationService.Service;

import com.flight.ReservationService.CustomException.BuySeatNotAvailable;
import com.flight.ReservationService.CustomException.SeatAlreadySoldException;
import com.flight.ReservationService.Entity.Enum.State;
import com.flight.ReservationService.Entity.Flight;
import com.flight.ReservationService.Entity.Seat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {
    @Mock
    private FlightService flightService;

    @Mock
    private SeatService seatService;

    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    void testBuySeat_SuccessfulPurchase() {
        // Arrange
        String seatNumber = "A1";
        String flightNumber = "FL123";
        Flight mockFlight = new Flight();
        mockFlight.setFlightNumber(flightNumber);
        mockFlight.setPlannedTime(LocalDateTime.now().plusHours(3));

        Seat mockSeat = new Seat();
        mockSeat.setNumber(seatNumber);
        mockSeat.setState(State.PURCHASABLE);
        mockSeat.setPlane(mockFlight.getPlane());

        when(flightService.findByFlightNumber(flightNumber)).thenReturn(mockFlight);
        when(seatService.findByNumberAndPlain(seatNumber, mockFlight.getPlane())).thenReturn(mockSeat);
        when(seatService.save(mockSeat)).thenReturn(mockSeat);

        // Act
        Seat purchasedSeat = purchaseService.buySeat(seatNumber, flightNumber);

        // Assert
        assertEquals(State.SOLD, purchasedSeat.getState());
        verify(seatService).save(mockSeat); // Ensure save was called
    }

    @Test
    void testBuySeat_ThrowsSeatAlreadySoldException_WhenSeatNotPurchasable() {
        // Arrange
        String seatNumber = "A1";
        String flightNumber = "FL123";
        Flight mockFlight = new Flight();
        mockFlight.setFlightNumber(flightNumber);
        mockFlight.setPlannedTime(LocalDateTime.now().plusHours(3));

        Seat mockSeat = new Seat();
        mockSeat.setNumber(seatNumber);
        mockSeat.setState(State.SOLD);
        mockSeat.setPlane(mockFlight.getPlane());

        when(flightService.findByFlightNumber(flightNumber)).thenReturn(mockFlight);
        when(seatService.findByNumberAndPlain(seatNumber, mockFlight.getPlane())).thenReturn(mockSeat);

        assertThrows(SeatAlreadySoldException.class, () -> purchaseService.buySeat(seatNumber, flightNumber));
        verify(seatService, never()).save(any(Seat.class));
    }

    @Test
    void testBuySeat_ThrowsBuySeatNotAvailable_WhenFlightIsInLessThan2Hours() {
        // Arrange
        String seatNumber = "A1";
        String flightNumber = "FL123";
        Flight mockFlight = new Flight();
        mockFlight.setFlightNumber(flightNumber);
        mockFlight.setPlannedTime(LocalDateTime.now().plusHours(1)); // Less than 2 hours

        Seat mockSeat = new Seat();
        mockSeat.setNumber(seatNumber);
        mockSeat.setState(State.PURCHASABLE);
        mockSeat.setPlane(mockFlight.getPlane());

        when(flightService.findByFlightNumber(flightNumber)).thenReturn(mockFlight);
        when(seatService.findByNumberAndPlain(seatNumber, mockFlight.getPlane())).thenReturn(mockSeat);

        assertThrows(BuySeatNotAvailable.class, () -> purchaseService.buySeat(seatNumber, flightNumber));
        verify(seatService, never()).save(any(Seat.class)); // Ensure save was not called
    }

    @Test
    void testBuySeat_ThrowsSeatAlreadySoldException_WhenOptimisticLockingFailure() {
        // Arrange
        String seatNumber = "A1";
        String flightNumber = "FL123";
        Flight mockFlight = new Flight();
        mockFlight.setFlightNumber(flightNumber);
        mockFlight.setPlannedTime(LocalDateTime.now().plusHours(3));

        Seat mockSeat = new Seat();
        mockSeat.setNumber(seatNumber);
        mockSeat.setState(State.PURCHASABLE);
        mockSeat.setPlane(mockFlight.getPlane());

        when(flightService.findByFlightNumber(flightNumber)).thenReturn(mockFlight);
        when(seatService.findByNumberAndPlain(seatNumber, mockFlight.getPlane())).thenReturn(mockSeat);
        when(seatService.save(mockSeat)).thenThrow(OptimisticLockingFailureException.class);

        assertThrows(SeatAlreadySoldException.class, () -> purchaseService.buySeat(seatNumber, flightNumber));
        verify(seatService).save(mockSeat);
    }

}