package sber.ru.terminal.dto.clientdto.converters;

import org.springframework.stereotype.Service;
import sber.ru.terminal.dto.clientdto.CardDto;
import sber.ru.terminal.entity.Card;

@Service
public class CardConverter {

    public CardDto convertCardToCardDto(Card card){

        CardDto cardDto = new CardDto();
        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setCurrency(card.getCurrency());
        cardDto.setAmount(card.getAmount());
        cardDto.setValid(card.getValid());

        return cardDto;
    }


}
