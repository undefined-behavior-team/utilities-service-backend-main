package ru.service.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.utilities.entity.AdminUser;
import ru.service.utilities.entity.Application;
import ru.service.utilities.entity.ApplicationClient;
import ru.service.utilities.entity.Client;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    List<Application> findByAdminUser(AdminUser adminUser);

    Optional<Application> findByIdAndAdminUser(UUID id, AdminUser adminUser);
}
