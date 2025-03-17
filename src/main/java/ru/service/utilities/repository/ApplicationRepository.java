package ru.service.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.utilities.entity.Application;


import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {
}
