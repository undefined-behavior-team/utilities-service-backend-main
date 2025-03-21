package ru.service.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.utilities.entity.Application;
import ru.service.utilities.entity.ApplicationClient;
import ru.service.utilities.entity.Client;
import ru.service.utilities.entity.MeterReading;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationClientRepository extends JpaRepository<ApplicationClient, UUID> {
    List<ApplicationClient> findByClient(Client client);
    Optional<ApplicationClient> findByClientAndApplicationId(Client client, UUID uuid);
}
