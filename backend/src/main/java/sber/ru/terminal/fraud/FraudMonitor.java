package sber.ru.terminal.fraud;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sber.ru.terminal.controllers.requests.TransferCardToCardApiRequest;
import sber.ru.terminal.entity.Card;
import sber.ru.terminal.entity.CreditCard;
import sber.ru.terminal.exceptions.FraudException;
import sber.ru.terminal.repositories.CardRepository;
import sber.ru.terminal.repositories.UserRepository;
import java.util.stream.Collectors;

@Service
public class FraudMonitor {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;


    public boolean checkTranferCardToCard(TransferCardToCardApiRequest transferCardToCardApiRequest){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean cardList = userRepository
                .findByUsername(auth.getName()).get().getClient().getAccount()
                .stream()
                .flatMap(m -> m.getCards()
                        .stream()).map(Card::getCardNumber)
                .collect(Collectors.toList())
                .contains(transferCardToCardApiRequest.getCardSender());

        Card cardSender = cardRepository.findByCardNumber(transferCardToCardApiRequest.getCardSender());
        Card cardRecipient = cardRepository.findByCardNumber(transferCardToCardApiRequest.getCardRecipient());

//        if(!(cardRecipient instanceof CreditCard)){
//            if(!cardSender.getCurrency().equals(cardRecipient.getCurrency())){
//                throw new FraudException("Невозможен перевод с "+ cardSender.getCurrency()+
//                        " карты на "+cardRecipient.getCurrency()+" карту" );
//            }
//        }

        if(cardSender.getCardNumber().equals(cardRecipient.getCardNumber())){
            throw new FraudException("Вы не можете переводить средства на карту с которой отправляете перевод");
        }

        if(cardRecipient instanceof CreditCard){
            if(!cardSender.getCurrency().equals("RUB")){
                throw new FraudException("Погашение кредита возможно только в RUB ");
            }
        }

        if(!cardList){
            throw new FraudException("У вас нету карты: "+ transferCardToCardApiRequest.getCardSender());
        }
//
        if(cardRepository.findByCardNumber(transferCardToCardApiRequest.getCardRecipient())==null){
            throw new FraudException("Карты ПОЛУЧАТЕЛЯ с номером: "
                    +transferCardToCardApiRequest.getCardRecipient()+
                    " не существует");
        }
//
        if(cardRepository.findByCardNumber(transferCardToCardApiRequest.getCardSender()).getAmount()
                .compareTo(transferCardToCardApiRequest.getAmount()) == -1){
            throw new FraudException("Недостаточно средств для осуществления перевода с карту на карту");
        }

        return true;

    }




}
