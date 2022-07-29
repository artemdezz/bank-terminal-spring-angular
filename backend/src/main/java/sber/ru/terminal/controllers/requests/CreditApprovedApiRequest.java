package sber.ru.terminal.controllers.requests;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
public class CreditApprovedApiRequest {

    private Long id;
    private BigDecimal limitAmount;
    private String currency;
    @DecimalMin(value = "0.01", message = "Кредитная ставка не может быть меньше 10")
    private BigDecimal creditPercentage;
    private Long client_id;
}
