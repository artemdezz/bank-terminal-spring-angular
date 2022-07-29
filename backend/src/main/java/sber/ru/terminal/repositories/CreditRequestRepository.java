package sber.ru.terminal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sber.ru.terminal.entity.CreditRequest;

@Repository
public interface CreditRequestRepository extends JpaRepository<CreditRequest, Long> {
}
