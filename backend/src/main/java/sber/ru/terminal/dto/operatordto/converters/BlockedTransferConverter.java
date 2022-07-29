package sber.ru.terminal.dto.operatordto.converters;

import org.springframework.stereotype.Service;
import sber.ru.terminal.dto.operatordto.BlockedTransferDto;
import sber.ru.terminal.entity.BlockedTransfer;

@Service
public class BlockedTransferConverter {

    public BlockedTransferDto convertBlockedTransferToBlockedTransferDto(BlockedTransfer blockedTransfer){


        BlockedTransferDto blockedTransferDto = new BlockedTransferDto();
        blockedTransferDto.setId(blockedTransfer.getId());
        blockedTransferDto.setTypeTransfer(blockedTransfer.getTypeTransfer());
        blockedTransferDto.setCurrency(blockedTransfer.getCurrency());
        blockedTransferDto.setAmount(blockedTransfer.getAmount());
        blockedTransferDto.setCardSender(blockedTransfer.getCardSender());
        blockedTransferDto.setCardRecepient(blockedTransfer.getCardRecepient());


        return blockedTransferDto;
    }
}
