package sber.ru.terminal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sber.ru.terminal.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
