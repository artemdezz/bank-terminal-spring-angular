package sber.ru.terminal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sber.ru.terminal.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
