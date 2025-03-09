package ru.service.utilities.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "address_client")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressClient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    @Column(length = 50)
    private String town;

    @Column(length = 150)
    private String street;

    @Column(length = 20)
    private String house;

    @Column(length = 10)
    private String building;

    @Column(length = 10)
    private String apartment;

}
