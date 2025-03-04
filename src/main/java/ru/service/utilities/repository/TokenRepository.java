package ru.service.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.utilities.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

}
