package sber.ru.terminal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sber.ru.terminal.controllers.requests.CreditApprovedApiRequest;
import sber.ru.terminal.dto.operatordto.BlockedTransferDto;
import sber.ru.terminal.dto.operatordto.CreditRequestDto;
import sber.ru.terminal.services.OperatorCreditService;
import sber.ru.terminal.services.OperatorTransferService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/operator")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OperatorController {

    @Autowired
    private OperatorTransferService operatorTransferService;

    @Autowired
    private OperatorCreditService operatorCreditService;

    // Получить все заявки на кредит
    @GetMapping("/credit-requests")
    public List<CreditRequestDto> getAllCreditRequests() {
        return operatorCreditService.getAllCreditRequests();
    }

    // Одобрить кредит
    @PostMapping("/confirm_credit")
    public String confirmCredit(@Valid @RequestBody CreditApprovedApiRequest creditApprovedApiRequest) {
        return operatorCreditService.confirmCredit(creditApprovedApiRequest);
    }

    // Отказать в кредите
    @PostMapping("/decline_credit")
    public String declineCredit(@RequestBody CreditApprovedApiRequest creditApprovedApiRequest) {
        return operatorCreditService.declineCreditRequest(creditApprovedApiRequest);
    }

    // Получить все заблокированные переводы
    @GetMapping("/blocked-transfers")
    public List<BlockedTransferDto> getBlockedTrasnfers() {
        return operatorTransferService.getBlockedTrasnfers();
    }

    // Одобрить заблокированный перевод
    @PostMapping("/accept-transfer")
    public String acceptTransfer(@RequestBody BlockedTransferDto blockedTransferDto){
        return operatorTransferService.approveTransferCardToCard(blockedTransferDto);
    }

    // Окончательно заблокировать перевод (ДОДЕЛАТЬ)
    @PostMapping("/block-transfer")
    public String blockTransfer(@RequestBody BlockedTransferDto blockedTransferDto){
        return operatorTransferService.blockTransferCardToCard(blockedTransferDto);
    }






}
