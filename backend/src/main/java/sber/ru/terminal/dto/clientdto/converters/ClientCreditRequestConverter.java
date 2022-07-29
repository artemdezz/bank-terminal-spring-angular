package sber.ru.terminal.dto.clientdto.converters;

import org.springframework.stereotype.Service;
import sber.ru.terminal.dto.clientdto.ClientCreditRequestDto;
import sber.ru.terminal.entity.CreditRequest;

@Service
public class ClientCreditRequestConverter {

    public ClientCreditRequestDto convertClientCreditRequestToClientCreditRequestDto(CreditRequest creditRequest){

        ClientCreditRequestDto clientCreditRequestDto = new ClientCreditRequestDto();
        clientCreditRequestDto.setCurrency(creditRequest.getCurrency());
        clientCreditRequestDto.setLimitAmount(creditRequest.getLimitAmount());
        clientCreditRequestDto.setStatus(creditRequest.getStatus());

        return clientCreditRequestDto;
    }

}
