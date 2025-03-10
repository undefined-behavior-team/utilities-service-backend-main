package ru.service.utilities.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Table (name = "client_homeowner")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientHomeowner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "homeowner_id", referencedColumnName = "id", nullable = false)
    private AdminUser adminUser;
}
