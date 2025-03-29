package ru.service.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.service.utilities.entity.Client;
import ru.service.utilities.entity.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByClient(Client client);
}
