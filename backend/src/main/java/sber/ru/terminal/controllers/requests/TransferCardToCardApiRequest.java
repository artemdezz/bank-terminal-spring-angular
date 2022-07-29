package sber.ru.terminal.controllers.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@Getter
@Setter
public class TransferCardToCardApiRequest {

    private String cardSender;
    private String cardRecipient;
    @DecimalMin(value = "0.01", message = "Сумма для перевода не может быть меньше 0.01")
    private BigDecimal amount;
    //new
    private String currency;

}
