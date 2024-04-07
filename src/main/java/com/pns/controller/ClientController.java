package com.pns.controller;

import com.pns.dto.ClientDto;
import com.pns.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/save-client")
    public ResponseEntity<?> save(@RequestBody ClientDto client) {
        return ResponseEntity.ok(clientService.save(client));
    }
    @PostMapping("/find-all-clients")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(clientService.findAll());
    }

    @PostMapping("/find-client-by-id")
    public ResponseEntity<?> findById(Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PostMapping("/delete-client")
    public void delete(Long id) {
        clientService.delete(id);
    }

    @PostMapping("/find-by-clientId")
    public ResponseEntity<?> findByClientId(Long clientId) {
        return ResponseEntity.ok(clientService.findByClientId(clientId));
    }

    @PostMapping("/find-by-clientId-and-browser")
    public ResponseEntity<?> findByClientIdAndBrowser(Long clientId, String browser) {
        return ResponseEntity.ok(clientService.findByClientIdAndBrowser(clientId, browser));
    }
}
