package sber.ru.terminal.dto.clientdto;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Date;

@Data
public class CardDto {

    private String cardNumber;
    private String currency;
    private BigDecimal amount;
    private Date valid;

}
