package sber.ru.terminal.dto.clientdto;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ClientCreditRequestDto {

    private BigDecimal limitAmount;
    private String currency;
    private String status;

}
