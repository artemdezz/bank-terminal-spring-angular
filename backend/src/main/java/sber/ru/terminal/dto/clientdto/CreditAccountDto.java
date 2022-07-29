package sber.ru.terminal.dto.clientdto;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
public class CreditAccountDto {

    private String accountNumber;
    private String currency;
    private BigDecimal amount;
    private Date beginCredit;
    private Date endCredit;
    private List<CreditCardDto> cards;
}
