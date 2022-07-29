package sber.ru.terminal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sber.ru.terminal.entity.*;
import sber.ru.terminal.repositories.AccountRepository;
import sber.ru.terminal.repositories.CardRepository;
import sber.ru.terminal.repositories.CreditAccountRepository;
import sber.ru.terminal.repositories.HistoryOperationsRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@EnableScheduling
@Service
public class CheckCreditScheduling {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    @Autowired
    private HistoryOperationsRepository historyOperationsRepository;

    Date nowDate = new Date(System.currentTimeMillis());

    @Scheduled(cron = "0 0 * * * ?")
//    @Scheduled(fixedDelay = 2000)
    public void checkOverdueCredit(){
        List<CreditAccount> creditAccounts = creditAccountRepository.findAll();

        for(CreditAccount creditAccount: creditAccounts){
            CreditCard creditCards = (CreditCard) creditAccount.getCards().get(0);

            Date endCredit = creditAccount.getEndCredit();

            // Закрыть кредит
            if(nowDate.compareTo(endCredit) > 0 &&
                    (creditCards.getLimitAmount().add(creditCards.getPenalty()))
                            .compareTo(creditCards.getAmount()) <= 0){
                closeCredit(creditCards);
            }

            // Начислить пени
            if(nowDate.compareTo(endCredit) > 0 &&
                    (creditCards.getLimitAmount().add(creditCards.getPenalty()))
                            .compareTo(creditCards.getAmount()) > 0){
                accruePenalty(creditCards);
            }
        }

    }

    private void accruePenalty(CreditCard creditCards){

        CreditCard creditCard = (CreditCard)
                cardRepository.findByCardNumber(creditCards.getCardNumber());

        BigDecimal borderCalcPenaltyPercent = creditCard
                .getCreditPercentage().add(new BigDecimal("40.00"));
        BigDecimal maxCalcPenalty = (creditCard.getLimitAmount().multiply(borderCalcPenaltyPercent))
                .divide(new BigDecimal(100.00)).setScale(2);

        if(creditCard.getPenalty().compareTo(maxCalcPenalty) < 0){
            BigDecimal currencyAmount = creditCard.getAmount();
            BigDecimal limit = creditCards.getLimitAmount();
            BigDecimal remainDebt = limit.subtract(currencyAmount);
            BigDecimal percentDebt = remainDebt.multiply(new BigDecimal("0.01")).setScale(2);
            // Начисление пени
            creditCard.setPenalty(creditCard.getPenalty().add(percentDebt));
            BigDecimal percentCredit = creditCard.getCreditPercentage();

            HistoryOperations historyOperations = new HistoryOperations();
            historyOperations.setHistoryMessage(nowDate+" Вам начислено пени за кредит по карте "
                    +creditCard.getCardNumber());
            historyOperations.setClient(creditCard.getAccount().getClient());
            historyOperationsRepository.save(historyOperations);

            cardRepository.save(creditCard);
        }
        System.out.println(creditCard.getPenalty());
    }

    private void closeCredit(CreditCard creditCard){

        HistoryOperations historyOperations = new HistoryOperations();
        historyOperations.setHistoryMessage(nowDate+" Вы выплатили кредит по карте "
                +creditCard.getCardNumber());
        historyOperations.setClient(creditCard.getAccount().getClient());

        Account account = accountRepository.getById(creditCard.getAccount().getId());
        historyOperationsRepository.save(historyOperations);
        cardRepository.delete(creditCard);
        creditAccountRepository.delete(creditAccountRepository.getById(account.getId()));
        accountRepository.delete(account);

    }
}
