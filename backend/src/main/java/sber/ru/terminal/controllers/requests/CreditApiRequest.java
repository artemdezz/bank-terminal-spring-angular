package sber.ru.terminal.controllers.requests;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreditApiRequest {
    private String currency;
    private BigDecimal limitAmount;
}
