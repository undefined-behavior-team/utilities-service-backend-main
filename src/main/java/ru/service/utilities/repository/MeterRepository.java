package ru.service.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.utilities.entity.Meter;

import java.util.UUID;

@Repository
public interface MeterRepository extends JpaRepository<Meter, UUID> {
}

