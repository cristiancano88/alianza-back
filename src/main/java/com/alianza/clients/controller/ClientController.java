package com.alianza.clients.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alianza.clients.model.dto.ClientRequestDTO;
import com.alianza.clients.model.dto.ClientResponseDTO;
import com.alianza.clients.service.ClientService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> saveClient(@Validated @RequestBody ClientRequestDTO clientRequestDTO) {
        ClientResponseDTO clientResponseDTO = clientService.saveClient(clientRequestDTO);
        return new ResponseEntity<>(clientResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        List<ClientResponseDTO> clients = clientService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClientResponseDTO>> searchClients(@RequestParam String term) {
        List<ClientResponseDTO> clients = clientService.searchClients(term);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/search-by-filters")
    public ResponseEntity<List<ClientResponseDTO>> searchClients(@RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email) {
        List<ClientResponseDTO> clients = clientService.searchClients(name, phone, email);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

}
