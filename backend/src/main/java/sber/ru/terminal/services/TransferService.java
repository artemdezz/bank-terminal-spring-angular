package sber.ru.terminal.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.ru.terminal.controllers.requests.TransferCardToCardApiRequest;
//import sber.ru.terminal.entity.DebitCard;
import sber.ru.terminal.entity.BlockedTransfer;
import sber.ru.terminal.entity.Card;
import sber.ru.terminal.entity.HistoryOperations;
import sber.ru.terminal.fraud.FraudMonitor;
import sber.ru.terminal.repositories.BlockedTransferRepository;
import sber.ru.terminal.repositories.CardRepository;
import sber.ru.terminal.repositories.ClientRepository;
//import sber.ru.terminal.repositories.DebitCardRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

@Service
public class TransferService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private FraudMonitor fraudMonitor;

    @Autowired
    private HistoryOperationsService historyOperationsService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BlockedTransferRepository blockedTransferRepository;


    public String tranferCardToCard(TransferCardToCardApiRequest transferCardToCardApiRequest){

        // Фрод монитор
        if(!fraudMonitor.checkTranferCardToCard(transferCardToCardApiRequest)){
            return "Неудача";
        }

        Card cardSender = cardRepository.findByCardNumber(transferCardToCardApiRequest.getCardSender());
        Card cardRecipient = cardRepository.findByCardNumber(transferCardToCardApiRequest.getCardRecipient());

        BigDecimal tranferAmount = convertedAmount(transferCardToCardApiRequest.getAmount(),
                cardSender.getCurrency(), cardRecipient.getCurrency());

        // Комиссия
        BigDecimal transferFee = new BigDecimal(0);
        if(!transferCardToCardApiRequest.getCardRecipient().startsWith("540436")){
            transferFee = new BigDecimal(10);
            transferCardToCardApiRequest.setAmount(transferCardToCardApiRequest.getAmount().subtract(transferFee));
        }

        cardSender.setAmount(cardSender.getAmount()
                .subtract(transferCardToCardApiRequest.getAmount().add(transferFee)));

        // Блокировка перевода с карты на карту, если amount = 77
        if(transferCardToCardApiRequest.getAmount().compareTo(new BigDecimal(77)) == 0){
            cardRepository.save(cardSender);
            BlockedTransfer blockedTransfer = new BlockedTransfer();
            blockedTransfer.setTypeTransfer("CTC");
            blockedTransfer.setAmount(transferCardToCardApiRequest.getAmount());
            blockedTransfer.setCurrency(cardSender.getCurrency());
            blockedTransfer.setCardSender(cardSender.getCardNumber());
            blockedTransfer.setCardRecepient(cardRecipient.getCardNumber());
            blockedTransfer.setClient(cardSender.getAccount().getClient());
            blockedTransferRepository.save(blockedTransfer);
            return "Ваш перевод заблокирован, ожидайте подтверждения оператора";
        }

        cardRecipient.setAmount(cardRecipient.getAmount().add(tranferAmount));
        cardRepository.save(cardSender);
        cardRepository.save(cardRecipient);

        // Сохранение истории операции
        return historyOperationsService.saveTransferCardToCard(cardSender,
                cardRecipient,
                transferCardToCardApiRequest.getAmount(), cardSender.getCurrency());
    }


    public BigDecimal convertedAmount(BigDecimal transferAmount,
                                       String senderCurrency, String recepientCurrency){
        HashMap<String, Double> currencyRates = new HashMap<>();
        currencyRates.put("EUR", 1.00);
        currencyRates.put("USD", 1.05);
        currencyRates.put("RUB", 65.48);

        // Исправить конвертацию c BigDecimal
        double convertedCurrency = transferAmount.doubleValue()
                /(currencyRates.get(senderCurrency)/currencyRates.get(recepientCurrency));
        return BigDecimal.valueOf(convertedCurrency)
                .setScale(2, RoundingMode.HALF_UP);
    }


}
