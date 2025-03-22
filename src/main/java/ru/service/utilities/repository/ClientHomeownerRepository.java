package ru.service.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.utilities.entity.AdminUser;
import ru.service.utilities.entity.Client;
import ru.service.utilities.entity.ClientHomeowner;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientHomeownerRepository extends JpaRepository<ClientHomeowner, UUID> {
    boolean existsByClientAndAdminUser(AdminUser adminUser, Client client);

    Optional<ClientHomeowner> findByClient(Client client);
}
