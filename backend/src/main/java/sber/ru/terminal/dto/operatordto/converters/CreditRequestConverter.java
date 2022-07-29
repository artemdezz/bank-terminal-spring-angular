package sber.ru.terminal.dto.operatordto.converters;

import org.springframework.stereotype.Service;
import sber.ru.terminal.dto.operatordto.CreditRequestDto;
import sber.ru.terminal.entity.CreditRequest;

@Service
public class CreditRequestConverter {

    public CreditRequestDto convertAccountToAccountDto(CreditRequest creditRequest){

        CreditRequestDto creditRequestDto = new CreditRequestDto();
        creditRequestDto.setId(creditRequest.getId());
        creditRequestDto.setCurrency(creditRequest.getCurrency());
        creditRequestDto.setLimitAmount(creditRequest.getLimitAmount());
        creditRequestDto.setClient_id(creditRequest.getClient().getId());
        creditRequestDto.setName(creditRequest.getClient().getName());
        creditRequestDto.setLastName(creditRequest.getClient().getLastName());
        creditRequestDto.setPatronymic(creditRequest.getClient().getPatronymic());

        return creditRequestDto;
    }
}
