package com.pns.mapper;

import com.pns.dto.ClientDto;
import com.pns.entity.Client;

import java.util.List;

public class ClientMapper {

    private static final ClientMapper instance = new ClientMapper();


    private ClientMapper(){

    }


    public static ClientMapper getInstance(){
        return instance;
    }


    public Client dtoToEntity(ClientDto dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setBrowser(dto.getBrowser());
        client.setClientId(dto.getClientId());
        return client;
    }



    public ClientDto entityToDto(Client client) {
        ClientDto dto = new ClientDto();
        dto.setId(client.getId());
        dto.setBrowser(client.getBrowser());
        dto.setClientId(client.getClientId());
        return dto;
    }

    public List<ClientDto> entitiesToDto(List<Client> clients) {
        return clients.stream().map(this::entityToDto).toList();
    }

    public List<Client> dtosToEntities(List<ClientDto> dtos) {
        return dtos.stream().map(this::dtoToEntity).toList();
    }
}
