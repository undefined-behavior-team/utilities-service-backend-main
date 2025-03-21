package ru.service.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.utilities.entity.AddressClient;
import ru.service.utilities.entity.Client;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressClientRepository extends JpaRepository<AddressClient, UUID> {

    Optional<AddressClient> findByClient(Client client);
}
