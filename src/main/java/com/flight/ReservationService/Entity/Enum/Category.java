package com.flight.ReservationService.Entity.Enum;

import lombok.Getter;

@Getter
public enum Category {
    ECONOMY("EC"),
    BUSINESS("BS"),
    FIRST_CLASS("FC");

    private final String shortening;

    Category(String shortening) {
        this.shortening = shortening;
    }

}
