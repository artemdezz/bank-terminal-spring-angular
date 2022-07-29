package sber.ru.terminal.dto.operatordto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreditRequestDto {

    private Long id;
    private BigDecimal limitAmount;
    private String currency;
    private Long client_id;
    private String name;
    private String lastName;
    private String patronymic;

}
