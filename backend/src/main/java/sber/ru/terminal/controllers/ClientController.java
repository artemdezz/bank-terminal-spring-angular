package sber.ru.terminal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sber.ru.terminal.controllers.requests.CreditApiRequest;
import sber.ru.terminal.controllers.requests.CreditApprovedApiRequest;
import sber.ru.terminal.controllers.requests.TransferCardToCardApiRequest;
import sber.ru.terminal.dto.clientdto.*;
import sber.ru.terminal.entity.Client;
import sber.ru.terminal.entity.CreditRequest;
import sber.ru.terminal.repositories.*;
import sber.ru.terminal.services.ClientCreditService;
import sber.ru.terminal.services.ClientService;
import sber.ru.terminal.services.TransferService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClientController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransferService transferService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientCreditService clientCreditService;

    @Autowired
    private ClientService clientService;

    // Получить ФИО клиента
    @GetMapping("/fullname")
    public ClientDto getClientsFullName() {
        return clientService.getClientsFullName();
    }

    // Получить счета клиента
    @GetMapping("/accounts")
    public List<AccountDto> getAllAccount() {
        return clientService.getSimpleAccounts();
    }

    // Получить кредитные счета клиента
    @GetMapping("/credit_accounts")
    public List<CreditAccountDto> getAllCreditAccount() {
        return clientService.getCreditAccounts();
    }

    // Получить все номера карт клиента
    @GetMapping("/allcardnumber")
    public List<String> getAllCardNumber(){
        return clientService.getAllCardNumberClients();
    }

    // Для проверки УБРАТЬ
    @GetMapping("/client")
    public Client operator12() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = userRepository.findByUsername(auth.getName()).get().getClient();
        return client;
    }

    // Для проверки УБРАТЬ
    @GetMapping("/allclient")
    public List<Client> operator123() {
        List<Client> client = clientRepository.findAll();
        return client;
    }

    // Перевод с карты на карту
    @PostMapping("/transfer_ctc")
    public String transferCardToCard(@Valid @RequestBody TransferCardToCardApiRequest transferCardToCardApiRequest) {
        return transferService.tranferCardToCard(transferCardToCardApiRequest);
    }

    // Оформить заявку на кредит
    @PostMapping("/credit_request")
    public CreditRequest doCreditRequest(@RequestBody CreditApiRequest creditApiRequest) {
        return clientCreditService.doCreditRequest(creditApiRequest);
    }

    // Получить заявки на кредит клиента
    @GetMapping("/credit_requests")
    public List<ClientCreditRequestDto> getCreditRequests() {
        return clientCreditService.getCreditRequests();
    }

    // Получить историю клиента
    @GetMapping("/history")
    public List<String> getClientHistoryOperation() {
        return clientService.getClientHistoryOperation();
    }

    // Получить одобренные кредиты
    @GetMapping("/credit_approveds")
    public List<CreditApprovedDto> getCreditApproveds() {
        return clientCreditService.getCreditApproveds();
    }

    // Оформить предложенный кредит
    @PostMapping("/credit_accept")
    public String acceptCredit(@RequestBody CreditApprovedApiRequest creditApprovedApiRequest) {
        return clientCreditService.acceptCredit(creditApprovedApiRequest);
    }

    // Отказаться от предложения на кредит
    @PostMapping("/decline_credit")
    public String declineCredit(@RequestBody CreditApprovedApiRequest creditApprovedApiRequest) {
        return clientCreditService.deleteCreditRequest(creditApprovedApiRequest.getId());
    }


}
