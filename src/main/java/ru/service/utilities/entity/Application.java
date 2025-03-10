package ru.service.utilities.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "application")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private short applicationType;

    @Column(length = 50)
    private String name;

    @Column(length = 1024)
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int status;

    @OneToMany(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<ApplicationClient> applicationClients;

    @ManyToOne
    @JoinColumn(name = "address_id",referencedColumnName = "id")
    private AddressClient addressClient;
}
