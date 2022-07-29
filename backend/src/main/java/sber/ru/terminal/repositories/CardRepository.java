package sber.ru.terminal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sber.ru.terminal.entity.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Card findByCardNumber(String cardNumber);
}
