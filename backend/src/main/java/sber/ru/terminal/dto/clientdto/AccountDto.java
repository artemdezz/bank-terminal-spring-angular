package sber.ru.terminal.dto.clientdto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class AccountDto {

    private String accountNumber;
    private String currency;
    private BigDecimal amount;
    private List<CardDto> cards;

}
