package sber.ru.terminal.dto.operatordto;

import lombok.Data;
import sber.ru.terminal.entity.Client;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
public class BlockedTransferDto {

    private Long id;
    private String typeTransfer;
    private BigDecimal amount;
    private String currency;
    private String cardSender;
    private String cardRecepient;

}
