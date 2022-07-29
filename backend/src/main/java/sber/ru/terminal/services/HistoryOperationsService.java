package sber.ru.terminal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sber.ru.terminal.entity.Card;
import sber.ru.terminal.entity.Client;
//import sber.ru.terminal.entity.DebitCard;
import sber.ru.terminal.entity.HistoryOperations;
import sber.ru.terminal.repositories.HistoryOperationsRepository;
import sber.ru.terminal.repositories.UserRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryOperationsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryOperationsRepository historyOperationsRepository;


    // Добавить имя клиента
    public String saveTransferCardToCard(Card cardSender, Card cardRecepient, BigDecimal amount, String currency){

        HistoryOperations senderHistoryOperations = new HistoryOperations();
        senderHistoryOperations.setHistoryMessage(new Date(System.currentTimeMillis())+
                " Вы перевели с карты "+ cardSender.getCardNumber()+" на карту "
        +cardRecepient.getCardNumber()+" "+amount+" "+currency);
        senderHistoryOperations.setClient(cardSender.getAccount().getClient());

        boolean cardList = cardSender.getAccount().getClient().getAccount()
                .stream()
                .flatMap(m -> m.getCards()
                        .stream()).map(Card::getCardNumber)
                .collect(Collectors.toList())
                .contains(cardRecepient.getCardNumber());

        historyOperationsRepository.save(senderHistoryOperations);

        if(cardList){
            return senderHistoryOperations.getHistoryMessage();
        }

        HistoryOperations recepientHistoryOperations = new HistoryOperations();
        recepientHistoryOperations.setHistoryMessage(new Date(System.currentTimeMillis())+
                " Вам перевели с карты "+ cardSender.getCardNumber()+" на карту "
                +cardRecepient.getCardNumber()+" от "
                +" "+amount+" "+currency);
        recepientHistoryOperations.setClient(cardRecepient.getAccount().getClient());

        historyOperationsRepository.save(recepientHistoryOperations);

        return senderHistoryOperations.getHistoryMessage();
    }

}
