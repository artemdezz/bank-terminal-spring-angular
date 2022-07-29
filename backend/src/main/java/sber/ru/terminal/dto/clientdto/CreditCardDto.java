package sber.ru.terminal.dto.clientdto;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Date;

@Data
public class CreditCardDto {

    private String cardNumber;
    private String currency;
    private BigDecimal amount;
    private BigDecimal limitAmount;
    private BigDecimal creditPercentage;
    private BigDecimal penalty;
    private Date valid;

}
