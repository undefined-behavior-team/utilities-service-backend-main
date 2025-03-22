package ru.service.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.utilities.entity.Client;
import ru.service.utilities.entity.MeterReading;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReading, UUID> {
    List<MeterReading> findByClient(Client client);
}
