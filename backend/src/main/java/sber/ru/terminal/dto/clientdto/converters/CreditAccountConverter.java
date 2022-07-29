package sber.ru.terminal.dto.clientdto.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.ru.terminal.dto.clientdto.CreditAccountDto;
import sber.ru.terminal.entity.CreditAccount;
import sber.ru.terminal.entity.CreditCard;

import java.util.stream.Collectors;

@Service
public class CreditAccountConverter {

    @Autowired
    private CreditCardConverter creditCardConverter;

    public CreditAccountDto convertCreditAccountToCreditAccountDto(CreditAccount creditAccount){

        CreditAccountDto creditAccountDto = new CreditAccountDto();
        creditAccountDto.setAccountNumber(creditAccount.getAccountNumber());
        creditAccountDto.setCurrency(creditAccount.getCurrency());
        creditAccountDto.setAmount(creditAccount.getAmount());
        creditAccountDto.setBeginCredit(creditAccount.getBeginCredit());
        creditAccountDto.setEndCredit(creditAccount.getEndCredit());
        creditAccountDto.setCards(creditAccount.getCards().stream()
                .map(a -> creditCardConverter.convertCreditCardToCreditCardDto((CreditCard) a))
                .collect(Collectors.toList()));

        return creditAccountDto;
    }
}
