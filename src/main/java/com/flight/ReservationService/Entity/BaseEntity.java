package com.flight.ReservationService.Entity;

import com.flight.ReservationService.Entity.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;
    @Column(nullable = false)
    private LocalDateTime updatedTime;
    @Version
    private Integer version;

    @PrePersist
    protected void onCreate() {
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedTime = LocalDateTime.now();
    }
}
