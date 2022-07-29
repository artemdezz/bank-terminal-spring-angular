package sber.ru.terminal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sber.ru.terminal.entity.BlockedTransfer;

@Repository
public interface BlockedTransferRepository extends JpaRepository<BlockedTransfer, Long> {
}
