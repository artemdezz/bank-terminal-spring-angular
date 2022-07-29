package sber.ru.terminal.dto.clientdto.converters;

import org.springframework.stereotype.Service;
import sber.ru.terminal.dto.clientdto.CreditApprovedDto;
import sber.ru.terminal.entity.CreditApproved;

@Service
public class CreditApprovedConverter {


    public CreditApprovedDto convertCreditApprovedToCreditApprovedDto(CreditApproved creditApproved){

        CreditApprovedDto creditApprovedDto = new CreditApprovedDto();
        creditApprovedDto.setId(creditApproved.getId());
        creditApprovedDto.setLimitAmount(creditApproved.getLimitAmount());
        creditApprovedDto.setCurrency(creditApproved.getCurrency());
        creditApprovedDto.setCreditPercentage(creditApproved.getCreditPercentage());

        return creditApprovedDto;
    }


}
