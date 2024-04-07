package com.pns.service;

import com.pns.dto.ClientDto;
import com.pns.mapper.ClientMapper;
import com.pns.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;


    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }



    public ClientDto save(ClientDto clientDto){
        clientRepository.save(ClientMapper.getInstance().dtoToEntity(clientDto));
        return clientDto;
    }

    public List<ClientDto> findAll(){
        return ClientMapper.getInstance().entitiesToDto(clientRepository.findAll());
    }


    public ClientDto findById(Long id){
        return ClientMapper.getInstance().entityToDto(clientRepository.findById(id).get());
    }

    public void delete(Long id){
        clientRepository.deleteById(id);
    }

    public ClientDto findByClientId(Long clientId){
        return ClientMapper.getInstance().entityToDto(clientRepository.findByClientId(clientId));
    }


    public ClientDto findByClientIdAndBrowser(Long clientId, String browser){
        return ClientMapper.getInstance().entityToDto(clientRepository.findByClientIdAndBrowser(clientId, browser));
    }


}
