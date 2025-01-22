package com.flight.ReservationService.Entity;

import com.flight.ReservationService.Entity.Enum.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Airport extends BaseEntity{
    private String name;
    private String code;
    private String location;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Country country;


}
