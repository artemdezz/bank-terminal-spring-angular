package sber.ru.terminal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sber.ru.terminal.dto.clientdto.AccountDto;
import sber.ru.terminal.dto.clientdto.ClientDto;
import sber.ru.terminal.dto.clientdto.CreditAccountDto;
import sber.ru.terminal.dto.clientdto.converters.AccountConverter;
import sber.ru.terminal.dto.clientdto.converters.ClientConverter;
import sber.ru.terminal.dto.clientdto.converters.CreditAccountConverter;
import sber.ru.terminal.entity.Card;
import sber.ru.terminal.entity.Client;
import sber.ru.terminal.entity.CreditAccount;
import sber.ru.terminal.entity.HistoryOperations;
import sber.ru.terminal.repositories.ClientRepository;
import sber.ru.terminal.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientConverter clientConverter;

    @Autowired
    private AccountConverter accountConverter;

    @Autowired
    private CreditAccountConverter creditAccountConverter;

    public ClientDto getClientsFullName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return clientConverter.convertClientToClientDto
                (userRepository.findByUsername(auth.getName())
                        .get().getClient());
    }

    public List<AccountDto> getSimpleAccounts(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName())
                .get().getClient().getAccount().stream().filter(a -> !(a instanceof CreditAccount))
                .map(a -> accountConverter.convertAccountToAccountDto(a)).collect(Collectors.toList());
    }

    public List<CreditAccountDto> getCreditAccounts(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName())
                .get().getClient().getAccount().stream().filter(a -> a instanceof CreditAccount)
                .map(a -> creditAccountConverter.convertCreditAccountToCreditAccountDto((CreditAccount) a))
                .collect(Collectors.toList());
    }


    public List<String> getAllCardNumberClients(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository
                .findByUsername(auth.getName()).get().getClient().getAccount()
                .stream()
                .flatMap(m -> m.getCards()
                        .stream()).map(Card::getCardNumber)
                .collect(Collectors.toList());
    }


    public List<String> getClientHistoryOperation(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository
                .findByUsername(auth.getName()).get().getClient().getHistoryOperations()
                .stream()
                .map(HistoryOperations::getHistoryMessage)
                .collect(Collectors.toList());
    }

}
