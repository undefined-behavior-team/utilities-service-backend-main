package ru.service.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.utilities.entity.Application;
import ru.service.utilities.entity.ApplicationClient;

import java.util.UUID;

@Repository
public interface ApplicationClientRepository extends JpaRepository<ApplicationClient, UUID> {
}
