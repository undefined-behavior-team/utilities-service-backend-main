package ru.service.utilities.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "meter_reading")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "meter_id", referencedColumnName = "id", nullable = false)
    private Meter meter;

    @Column(nullable = false)
    private LocalDate createdAt;
}
