package sber.ru.terminal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sber.ru.terminal.controllers.requests.CreditApiRequest;
import sber.ru.terminal.controllers.requests.CreditApprovedApiRequest;
import sber.ru.terminal.dto.clientdto.ClientCreditRequestDto;
import sber.ru.terminal.dto.clientdto.CreditApprovedDto;
import sber.ru.terminal.dto.clientdto.converters.ClientCreditRequestConverter;
import sber.ru.terminal.dto.clientdto.converters.CreditApprovedConverter;
import sber.ru.terminal.entity.*;
import sber.ru.terminal.repositories.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientCreditService {

    long now = System.currentTimeMillis();
    Date sqlDate = new Date(now);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CreditApprovedConverter creditApprovedConverter;

    @Autowired
    private CreditApprovedRepository creditApprovedRepository;

    @Autowired
    private CreditRequestRepository creditRequestRepository;

    @Autowired
    private ClientCreditRequestConverter clientCreditRequestConverter;

    public String acceptCredit(CreditApprovedApiRequest creditApprovedApiRequest){

        deleteCreditRequest(creditApprovedApiRequest.getId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = userRepository.findByUsername(auth.getName()).get().getClient();

        newCreditAccount(creditApprovedApiRequest, client);

        return "Кредит оформлен";
    }


    public CreditRequest doCreditRequest(CreditApiRequest creditApiRequest){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = userRepository.findByUsername(auth.getName()).get().getClient();

        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setCurrency(creditApiRequest.getCurrency());
        creditRequest.setLimitAmount(creditApiRequest.getLimitAmount());
        creditRequest.setStatus("Ожидание оператора");
        creditRequest.setClient(client);

        creditRequestRepository.save(creditRequest);

        return creditRequest;
    }

    public List<ClientCreditRequestDto> getCreditRequests(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = userRepository.findByUsername(auth.getName()).get().getClient();

        return client.getCreditRequests().stream()
                .map(a -> clientCreditRequestConverter
                        .convertClientCreditRequestToClientCreditRequestDto(a)).collect(Collectors.toList());
    }

    public List<CreditApprovedDto> getCreditApproveds(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = userRepository.findByUsername(auth.getName()).get().getClient();

        return client.getCreditApproved().stream()
                .map(a -> creditApprovedConverter.convertCreditApprovedToCreditApprovedDto(a))
                .collect(Collectors.toList());
    }

    public String deleteCreditRequest(Long creditRequestId){
        creditApprovedRepository.delete(creditApprovedRepository.getById(creditRequestId));
        return "Вы отклонили предложение о кредите";
    }


    private void newCreditAccount(CreditApprovedApiRequest creditApprovedApiRequest, Client client){

        long generatedAccountNumber = 2399000000L + (long) (Math.random() * (2399099999L - 2399000000L));
        String accountNumber = "4070297805"+generatedAccountNumber;

        CreditAccount creditAccount = new CreditAccount();
        creditAccount.setAccountNumber(accountNumber);
        creditAccount.setCurrency(creditApprovedApiRequest.getCurrency());
        creditAccount.setAmount(new BigDecimal("0.0"));
        creditAccount.setBeginCredit(sqlDate);
        creditAccount.setEndCredit(Date.valueOf(sqlDate.toLocalDate().plusDays(30)));
        creditAccount.setClient(client);
        accountRepository.save(creditAccount);

        newCreditCard(creditApprovedApiRequest, creditAccount);

    }

    private void newCreditCard(CreditApprovedApiRequest creditApprovedApiRequest, Account account){

        long generatedCardNumber = 2399000000L + (long) (Math.random() * (2399099999L - 2399000000L));
        String cardNumber = "540436"+generatedCardNumber;

        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber(cardNumber);
        creditCard.setCurrency(creditApprovedApiRequest.getCurrency());
        creditCard.setAmount(creditApprovedApiRequest.getLimitAmount());
        creditCard.setValid(Date.valueOf(sqlDate.toLocalDate().plusDays(365)));
        creditCard.setCreditPercentage(creditApprovedApiRequest.getCreditPercentage());
        creditCard.setPenalty(new BigDecimal("0.00"));
        creditCard.setLimitAmount(creditApprovedApiRequest.getLimitAmount());
        creditCard.setAccount(account);
        cardRepository.save(creditCard);
    }


}
