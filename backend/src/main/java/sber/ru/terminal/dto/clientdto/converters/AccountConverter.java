package sber.ru.terminal.dto.clientdto.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.ru.terminal.dto.clientdto.AccountDto;
import sber.ru.terminal.entity.Account;
import java.util.stream.Collectors;

@Service
public class AccountConverter {

    @Autowired
    private CardConverter cardConverter;

    public AccountDto convertAccountToAccountDto(Account account){

        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setCurrency(account.getCurrency());
        accountDto.setAmount(account.getAmount());
        accountDto.setCards(account.getCards().stream()
                .map(a -> cardConverter.convertCardToCardDto(a))
                .collect(Collectors.toList()));

        return accountDto;
    }
}
