package sber.ru.terminal.dto.clientdto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreditApprovedDto {

    private Long id;
    private BigDecimal limitAmount;
    private String currency;
    private BigDecimal creditPercentage;

}
