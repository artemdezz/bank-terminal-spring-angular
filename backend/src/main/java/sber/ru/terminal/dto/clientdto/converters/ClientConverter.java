package sber.ru.terminal.dto.clientdto.converters;

import org.springframework.stereotype.Service;
import sber.ru.terminal.dto.clientdto.ClientDto;
import sber.ru.terminal.entity.Client;

@Service
public class ClientConverter {
    public ClientDto convertClientToClientDto(Client client){

        ClientDto clientDto = new ClientDto();
        clientDto.setName(client.getName());
        clientDto.setLastName(client.getLastName());
        clientDto.setPatronymic(client.getPatronymic());

        return clientDto;
    }

}
