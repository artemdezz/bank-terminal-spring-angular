package sber.ru.terminal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.ru.terminal.controllers.requests.CreditApprovedApiRequest;
import sber.ru.terminal.dto.operatordto.CreditRequestDto;
import sber.ru.terminal.dto.operatordto.converters.CreditRequestConverter;
import sber.ru.terminal.entity.CreditApproved;
import sber.ru.terminal.entity.HistoryOperations;
import sber.ru.terminal.repositories.ClientRepository;
import sber.ru.terminal.repositories.CreditApprovedRepository;
import sber.ru.terminal.repositories.CreditRequestRepository;
import sber.ru.terminal.repositories.HistoryOperationsRepository;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperatorCreditService {

    @Autowired
    private CreditApprovedRepository creditApprovedRepository;

    @Autowired
    private CreditRequestRepository creditRequestRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private HistoryOperationsRepository historyOperationsRepository;

    @Autowired
    private CreditRequestConverter creditRequestConverter;

    public List<CreditRequestDto> getAllCreditRequests(){
        return creditRequestRepository.findAll().stream()
                .map(a -> creditRequestConverter.convertAccountToAccountDto(a)).collect(Collectors.toList());
    }

    public String confirmCredit(CreditApprovedApiRequest creditApprovedApiRequest){

        CreditApproved creditApproved = new CreditApproved();
        creditApproved.setLimitAmount(creditApprovedApiRequest.getLimitAmount());
        creditApproved.setCurrency(creditApprovedApiRequest.getCurrency());
        creditApproved.setCreditPercentage(creditApprovedApiRequest.getCreditPercentage());
        creditApproved.setClient(clientRepository.findById(creditApprovedApiRequest.getClient_id()).get());
        creditApprovedRepository.save(creditApproved);

        deleteCreditRequest(creditApprovedApiRequest.getId());

        return "Кредит одобрен";
    }


    public String declineCreditRequest(CreditApprovedApiRequest creditApprovedApiRequest){

        HistoryOperations historyOperations = new HistoryOperations();
        historyOperations.setHistoryMessage(new Date(System.currentTimeMillis())+" Вам было отказано в кредите "+
                creditApprovedApiRequest.getLimitAmount()+" "+creditApprovedApiRequest.getCurrency());
        historyOperations.setClient(clientRepository.findById(creditApprovedApiRequest.getClient_id()).get());

        historyOperationsRepository.save(historyOperations);

        deleteCreditRequest(creditApprovedApiRequest.getId());

        return "В кредите отказано";
    }

    private void deleteCreditRequest(Long creditRequestId){
        creditRequestRepository.delete(creditRequestRepository.getById(creditRequestId));
    }
}
