package sber.ru.terminal.dto.clientdto.converters;

import org.springframework.stereotype.Service;
import sber.ru.terminal.dto.clientdto.CreditCardDto;
import sber.ru.terminal.entity.CreditCard;

@Service
public class CreditCardConverter {

    public CreditCardDto convertCreditCardToCreditCardDto(CreditCard creditCard){

        CreditCardDto creditCardDto = new CreditCardDto();
        creditCardDto.setCardNumber(creditCard.getCardNumber());
        creditCardDto.setCurrency(creditCard.getCurrency());
        creditCardDto.setAmount(creditCard.getAmount());
        creditCardDto.setLimitAmount(creditCard.getLimitAmount());
        creditCardDto.setCreditPercentage(creditCard.getCreditPercentage());
        creditCardDto.setPenalty(creditCard.getPenalty());
        creditCardDto.setValid(creditCard.getValid());

        return creditCardDto;
    }


}
