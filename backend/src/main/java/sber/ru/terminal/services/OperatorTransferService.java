package sber.ru.terminal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sber.ru.terminal.controllers.requests.TransferCardToCardApiRequest;
import sber.ru.terminal.dto.operatordto.BlockedTransferDto;
import sber.ru.terminal.dto.operatordto.converters.BlockedTransferConverter;
import sber.ru.terminal.entity.BlockedTransfer;
import sber.ru.terminal.entity.Card;
import sber.ru.terminal.entity.HistoryOperations;
import sber.ru.terminal.repositories.BlockedTransferRepository;
import sber.ru.terminal.repositories.CardRepository;
import sber.ru.terminal.repositories.HistoryOperationsRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperatorTransferService {

    @Autowired
    private HistoryOperationsRepository historyOperationsRepository;

    @Autowired
    private HistoryOperationsService historyOperationsService;

    @Autowired
    private TransferService transferService;


    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BlockedTransferRepository blockedTransferRepository;

    @Autowired
    private BlockedTransferConverter blockedTransferConverter;


    public List<BlockedTransferDto> getBlockedTrasnfers(){
        return blockedTransferRepository.findAll().stream()
                .map(a -> blockedTransferConverter.convertBlockedTransferToBlockedTransferDto(a))
                .collect(Collectors.toList());
    }

    public String approveTransferCardToCard(BlockedTransferDto blockedTransferDto){

        Card cardSender = cardRepository.findByCardNumber(blockedTransferDto.getCardSender());
        Card cardRecipient = cardRepository.findByCardNumber(blockedTransferDto.getCardRecepient());

        BigDecimal tranferAmount = transferService.convertedAmount(blockedTransferDto.getAmount(),
                cardSender.getCurrency(), cardRecipient.getCurrency());

//        cardSender.setAmount(cardSender.getAmount().subtract(blockedTransferDto.getAmount()));
        cardRecipient.setAmount(cardRecipient.getAmount().add(tranferAmount));

//        cardRepository.save(cardSender);
        cardRepository.save(cardRecipient);

        deleteBlockedTransfer(blockedTransferDto.getId());
        return historyOperationsService.saveTransferCardToCard(cardSender,
                cardRecipient,
                blockedTransferDto.getAmount(), cardSender.getCurrency());
    }

    public String blockTransferCardToCard(BlockedTransferDto blockedTransferDto){

        Card cardSender = cardRepository.findByCardNumber(blockedTransferDto.getCardSender());
        cardSender.setAmount(cardSender.getAmount().add(blockedTransferDto.getAmount()));
        cardRepository.save(cardSender);

        HistoryOperations historyOperations = new HistoryOperations();
        historyOperations.setHistoryMessage("Ваш перевод на сумму "
                +blockedTransferDto.getAmount()+" "+blockedTransferDto.getCurrency()+
                "был окончательно заблокирован");
        historyOperations.setClient(cardRepository.
                findByCardNumber(blockedTransferDto.getCardSender()).getAccount().getClient());
        historyOperationsRepository.save(historyOperations);
        deleteBlockedTransfer(blockedTransferDto.getId());
        return "Вы заблокировали перевод";
    }


    private void deleteBlockedTransfer(Long blockedTransferId){
        blockedTransferRepository.delete(blockedTransferRepository.getById(blockedTransferId));
    }

}
